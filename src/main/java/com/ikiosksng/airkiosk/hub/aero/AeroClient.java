package com.ikiosksng.airkiosk.hub.aero;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.xml.transform.StringSource;

import com.ikioskng.airkiosks.ws.types.ConfirmTicketResponse;
import com.ikioskng.airkiosks.ws.types.Gender;
import com.ikioskng.airkiosks.ws.types.PassengerDetail;
import com.ikioskng.airkiosks.ws.types.PersonName;
import com.ikioskng.airkiosks.ws.types.PnrResponse;
import com.ikioskng.airkiosks.ws.types.ReserveTicketResponse;
import com.ikioskng.airkiosks.ws.types.Ticket;
import com.ikiosksng.airkiosk.hub.reservations.pnr.Passenger;
import com.ikiosksng.airkiosk.hub.reservations.pnr.PassengerNameRecord;
import com.ikiosksng.airkiosk.hub.reservations.pnr.Payment;
import com.mycila.xmltool.CallBack;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLDocumentException;
import com.mycila.xmltool.XMLTag;

@Component
public class AeroClient {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AeroClient.class);

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy/MMM/dd hh:mm");

	@Value("${aeroclient.bsi.password}")
	private String password;

	@Value("${aeroclient.bsi.id}")
	private String id;

	@Value("${aeroclient.bsi.host}")
	private String host;

	@Value("${aeroclient.server.url}")
	private String aikioskServerUrl;

	public static class AVReq {
		String from;
		String to;
		Date date;
		String currency;

		public AVReq(String from, String to, Date date, String currency) {
			super();
			this.from = from;
			this.to = to;
			this.date = date;
			this.currency = currency;
		}
	};

	public static class Flight {
		private String flightNumber, fromCode, toCode;
		private Date departure;
		private Date arrival;
		private List<Fare> fares = new ArrayList<Fare>();

		public String getFlightNumber() {
			return flightNumber;
		}

		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}

		public Date getDeparture() {
			return departure;
		}

		public void setDeparture(Date departure) {
			this.departure = departure;
		}

		public Date getArrival() {
			return arrival;
		}

		public void setArrival(Date arrival) {
			this.arrival = arrival;
		}

		public List<Fare> getFares() {
			return fares;
		}

		public void setFares(List<Fare> fares) {
			this.fares = fares;
		}
		
		public String getFromCode() {
			return fromCode;
		}

		public void setFromCode(String fromCode) {
			this.fromCode = fromCode;
		}

		public String getToCode() {
			return toCode;
		}

		public void setToCode(String toCode) {
			this.toCode = toCode;
		}

		@Override
		public String toString() {
			return "Flight [flightNumber=" + flightNumber + ", departure="
					+ departure + ", arrival=" + arrival + ", fares=" + fares
					+ "]";
		}

	}
	
	public static class FlightDetails {
		private String flightCode, fromAirportCode, toAirportCode, month;
		private int day, year;
		
		public String getFlightCode() {
			return flightCode;
		}
		public void setFlightCode(String flightCode) {
			this.flightCode = flightCode;
		}
		public String getFromAirportCode() {
			return fromAirportCode;
		}
		public void setFromAirportCode(String fromAirportCode) {
			this.fromAirportCode = fromAirportCode;
		}
		public String getToAirportCode() {
			return toAirportCode;
		}
		public void setToAirportCode(String toAirportCode) {
			this.toAirportCode = toAirportCode;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public int getDay() {
			return day;
		}
		public void setDay(int day) {
			this.day = day;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		
	}
	
	public static class CardInfo {
		
		private String cardHolderName, cardType, cardNumber, cardExpMonth, cardExpYear, cardPin, cardEmail, cardFee, serviceFee, billingAddress1, billingAddress2, 
				billingCity, billingZip, billingState, billingCountry;

		public String getCardHolderName() {
			return cardHolderName;
		}

		public void setCardHolderName(String cardHolderName) {
			this.cardHolderName = cardHolderName;
		}

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
		}

		public String getCardNumber() {
			return cardNumber;
		}

		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}

		public String getCardExpMonth() {
			return cardExpMonth;
		}

		public void setCardExpMonth(String cardExpMonth) {
			this.cardExpMonth = cardExpMonth;
		}

		public String getCardExpYear() {
			return cardExpYear;
		}

		public void setCardExpYear(String cardExpYear) {
			this.cardExpYear = cardExpYear;
		}

		public String getCardPin() {
			return cardPin;
		}

		public void setCardPin(String cardPin) {
			this.cardPin = cardPin;
		}

		public String getCardEmail() {
			return cardEmail;
		}

		public void setCardEmail(String cardEmail) {
			this.cardEmail = cardEmail;
		}

		public String getCardFee() {
			return cardFee;
		}

		public void setCardFee(String cardFee) {
			this.cardFee = cardFee;
		}

		public String getServiceFee() {
			return serviceFee;
		}

		public void setServiceFee(String serviceFee) {
			this.serviceFee = serviceFee;
		}

		public String getBillingAddress1() {
			return billingAddress1;
		}

		public void setBillingAddress1(String billingAddress1) {
			this.billingAddress1 = billingAddress1;
		}

		public String getBillingAddress2() {
			return billingAddress2;
		}

		public void setBillingAddress2(String billingAddress2) {
			this.billingAddress2 = billingAddress2;
		}

		public String getBillingCity() {
			return billingCity;
		}

		public void setBillingCity(String billingCity) {
			this.billingCity = billingCity;
		}

		public String getBillingZip() {
			return billingZip;
		}

		public void setBillingZip(String billingZip) {
			this.billingZip = billingZip;
		}

		public String getBillingState() {
			return billingState;
		}

		public void setBillingState(String billingState) {
			this.billingState = billingState;
		}

		public String getBillingCountry() {
			return billingCountry;
		}

		public void setBillingCountry(String billingCountry) {
			this.billingCountry = billingCountry;
		} 
	}
	public static class VoucherInfo{
		private String number;
		private String pin;
		public VoucherInfo(String number, String pin) {
			super();
			this.number = number;
			this.pin = pin;
		}
		public String getNumber() {
			return number;
		}
		public String getPin() {
			return pin;
		}
	}
	public static class Fare {
		private String code;
		private String currency;
		private int classLevel;
		private BigDecimal adultFare;
		private BigDecimal adultTax;
		private BigDecimal childFare;
		private BigDecimal childTax;
		private BigDecimal infantFare;
		private BigDecimal infantTax;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public void setClassLevel(int classLevel) {
			this.classLevel = classLevel;
		}

		public int getClassLevel() {
			return classLevel;
		}

		public BigDecimal getAdultFare() {
			return adultFare;
		}

		public void setAdultFare(BigDecimal adultFare) {
			this.adultFare = adultFare;
		}

		public BigDecimal getAdultTax() {
			return adultTax;
		}

		public void setAdultTax(BigDecimal adultTax) {
			this.adultTax = adultTax;
		}

		public BigDecimal getChildFare() {
			return childFare;
		}

		public void setChildFare(BigDecimal childFare) {
			this.childFare = childFare;
		}

		public BigDecimal getChildTax() {
			return childTax;
		}

		public void setChildTax(BigDecimal childTax) {
			this.childTax = childTax;
		}

		public BigDecimal getInfantFare() {
			return infantFare;
		}

		public void setInfantFare(BigDecimal infantFare) {
			this.infantFare = infantFare;
		}

		public BigDecimal getInfantTax() {
			return infantTax;
		}

		public void setInfantTax(BigDecimal infantTax) {
			this.infantTax = infantTax;
		}

		@Override
		public String toString() {
			return "Fare [code=" + code + ", currency=" + currency
					+ ", adultFare=" + adultFare + ", adultTax=" + adultTax
					+ ", childFare=" + childFare + ", childTax=" + childTax
					+ ", infantFare=" + infantFare + ", infantTax=" + infantTax
					+ "]";
		}
	}

	public static class ReserveReq {
		
		FlightDetails outboundFlight, inboundFlight;
		String classTypeOutbound,classTypeInbound, currency;
		List<PassengerDetail> passengers;
		int numAdults, numChildren, numInfants;
		
		public ReserveReq(String currency, FlightDetails outboundFlight,
				FlightDetails inboundFlight, String classTypeOutbound, String classTypeInbound,
				List<PassengerDetail> passengers, int numAdults,
				int numChildren, int numInfants) {
			super();
			this.currency = currency;
			this.outboundFlight = outboundFlight;
			this.inboundFlight = inboundFlight;
			this.classTypeOutbound = classTypeOutbound;
			this.classTypeInbound = classTypeInbound;
			this.passengers = passengers;
			this.numAdults = numAdults;
			this.numChildren = numChildren;
			this.numInfants = numInfants;
		}
	}
	
	public static class PnrReq {
		
		String lastName, pnr;

		public PnrReq(String lastName, String pnr) {
			super();
			this.lastName = lastName;
			this.pnr = pnr;
		}
	}
	
	public static class ConfirmReq {
//		PNR
//		FOP
//		PAY_AMOUNT
//		CURRENCY

		String pnr, fop, pay_amount, currency;
		CardInfo cardInfo;
		List<VoucherInfo> vouchers;
		
		public ConfirmReq(String pnr, String fop, String pay_amount, String currency,
		CardInfo cardInfo) {
			super();
			this.pnr = pnr;
			this.fop = fop;
			this.pay_amount = pay_amount;
			this.currency = currency;
			this.cardInfo = cardInfo;
		}
		public ConfirmReq(String pnr,  String pay_amount, 	List<VoucherInfo> vouchers) {
					super();
					this.pnr = pnr;
					this.pay_amount = pay_amount;
					this.vouchers = vouchers;
		}

	}
	
	public List<Flight> availabilityRequest(final AVReq req) {

		return execute(new SessionCallBack<List<Flight>>() {

			@Override
			public List<Flight> doInSession(String terminalId) {
				Source request = XMLDoc
						.newDocument(true)
						.addRoot("AK_AV")
						.addTag("TERMINAL")
						.setText(terminalId)
						.gotoParent()
						.addTag("AGENT")
						.setText("LOSIFAJ.4104.410")
						.gotoParent()
						.addTag("IATA")
						.setText("LOSIFAJ")
						.gotoParent()
						.addTag("OFFICE")
						.setText("4104")
						.gotoParent()
						.addTag("FROM")
						.setText(req.from)
						.gotoParent()
						.addTag("TO")
						.setText(req.to)
						.gotoParent()
						.addTag("DATE")
						.setText(new SimpleDateFormat("ddMMMyyyy").format(req.date))
						.gotoParent()
						.addTag("CURRENCY").setText(req.currency).toSource();// later setText(req.currency)
				//System.out.println("request"+request.toString());
				Source response = sendAndRecevie(request);
				System.out.println("response"+response.toString());
				final List<Flight> result = new ArrayList<AeroClient.Flight>();
				extractFlights(response, result);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(result.toString());
				}
//				result.get(0).
				return result;
			}

			private void extractFlights(Source response,
					final List<Flight> result) {
				XMLDoc.from(response, false).gotoRoot()
						.forEach("AVAILABILITY/FLIGHT", new CallBack() {
							public void execute(XMLTag doc) {
								result.add(extractFlight(doc));
							}
						});
			}
		});
	}
	private Flight extractFlight(XMLTag flightNode) {
		Flight flight = new Flight();
		flight.setFlightNumber(flightNode.getAttribute("Flight_number"));
		String segments = flightNode.getAttribute("Segment");
		flight.setFromCode(segments.substring(0, 3));
		flight.setToCode(segments.substring(3, 6));
		String stdSta = flightNode.getAttribute("STD_STA");
		String[] times = stdSta.split("-");
		String flightDate = flightNode.getAttribute("Flight_date");
		try {
			flight.setDeparture(DATE_FORMAT.parse(flightDate + " " + times[0]));
			flight.setArrival(DATE_FORMAT.parse(flightDate + " " + times[1]));

			final List<Fare> fares = new ArrayList<Fare>();
			flightNode.forEach("CLASS", new CallBack() {
				public void execute(XMLTag classNode) {
					fares.add(extractFare(classNode));
				}
			});

			flight.setFares(fares);

		} catch (ParseException e) {
			throw new RuntimeException(
					"error while extracting depature / arrival time", e);
		}
		return flight;
	}
	private Fare extractFare(XMLTag fareNode) {
		Fare fare = new Fare();
		fare.setCode(fareNode.getAttribute("Class_code"));
		fare.setCurrency(fareNode.getAttribute("Currency"));
		fare.setClassLevel(Integer.parseInt(fareNode.getAttribute("Class_level")));
		fare.setAdultFare(getAttributeAsBigDecimal(fareNode, "Fare"));
		fare.setAdultTax(getAttributeAsBigDecimal(fareNode, "Adults_tax"));
		fare.setChildFare(getAttributeAsBigDecimal(fareNode, "Child"));
		fare.setChildTax(getAttributeAsBigDecimal(fareNode, "Kids_tax"));
		fare.setInfantFare(getAttributeAsBigDecimal(fareNode, "Infant"));
		fare.setInfantTax(getAttributeAsBigDecimal(fareNode, "Infants_tax"));
		return fare;
	}

	private BigDecimal getAttributeAsBigDecimal(XMLTag fareNode,
			String attributeName) {
		String value = fareNode.getAttribute(attributeName);
		if (StringUtils.hasLength(value)) {
			return new BigDecimal(value);
		} else {
			return new BigDecimal(0);
		}
	}
	
	public ReserveTicketResponse reserveTicketRequest(final ReserveReq reserveReq) {
		return execute(new SessionCallBack<ReserveTicketResponse>() {

			@Override
			public ReserveTicketResponse doInSession(String terminalId) {
				XMLTag request = XMLDoc.newDocument(true).addRoot("AK_EOT")						
				.addTag("TERMINAL")
				.setText(terminalId)
				.gotoParent()
				.addTag("AGENT")
				.setText("LOSIFAJ.4104.410")
				.gotoParent()
				.addTag("IATA")
				.setText("LOSIFAJ")
				.gotoParent()
				.addTag("OFFICE")
				.setText("4104")
				.gotoParent()
				.addTag("ID")
				.setText("7201078856624851")
				.gotoParent()
				.addTag("CURRENCY")
				.setText("NGN")
				.gotoParent()
				.addTag("FLIGHT")
				.setText(reserveReq.outboundFlight.getFlightCode())
				.gotoParent()
				.addTag("FROM")
				.setText(reserveReq.outboundFlight.getFromAirportCode())
				.gotoParent()
				.addTag("TO")
				.setText(reserveReq.outboundFlight.getToAirportCode())
				.gotoParent()
				.addTag("FLDAY")
				.setText(Integer.toString(reserveReq.outboundFlight.getDay()))
				.gotoParent()
				.addTag("FLMONTH")
				.setText(reserveReq.outboundFlight.getMonth())
				.gotoParent()
				.addTag("FLYEAR")
				.setText(Integer.toString(reserveReq.outboundFlight.getYear()))
				.gotoParent()
				.addTag("CLASS")
				.setText(reserveReq.classTypeOutbound);
				if(reserveReq.inboundFlight!=null) {
					request.gotoParent()
					.addTag("FLIGHT")
					.setText(reserveReq.inboundFlight.getFlightCode())
					.gotoParent()
					.addTag("FROM")
					.setText(reserveReq.inboundFlight.getFromAirportCode())
					.gotoParent()
					.addTag("TO")
					.setText(reserveReq.inboundFlight.getToAirportCode())
					.gotoParent()
					.addTag("FLDAY")
					.setText(Integer.toString(reserveReq.inboundFlight.getDay()))
					.gotoParent()
					.addTag("FLMONTH")
					.setText(reserveReq.inboundFlight.getMonth())
					.gotoParent()
					.addTag("FLYEAR")
					.setText(Integer.toString(reserveReq.inboundFlight.getYear()))
					.gotoParent()
					.addTag("CLASS")
					.setText(reserveReq.classTypeInbound);
				}
				request.gotoParent()
				.addTag("ADT")
				.setText(Integer.toString(reserveReq.numAdults));
				if(reserveReq.numChildren>0) { 
					request.gotoParent()
					.addTag("CHD")
					.setText(Integer.toString(reserveReq.numChildren));
				} if(reserveReq.numInfants>0) { 
					request.gotoParent()
					.addTag("INF")
					.setText(Integer.toString(reserveReq.numInfants));
				} 
				for (PassengerDetail passenger : reserveReq.passengers) {
					String title = passenger.getTitle().substring(0, (passenger.getTitle().length()-1)).toUpperCase();
					request.gotoParent()
					.addTag("TITLE")
					.setText(title)
					.gotoParent()
					.addTag("FNAME")
					.setText(passenger.getName().getFirstName())
					.gotoParent()
					.addTag("LNAME")
					.setText(passenger.getName().getLastName());
						String ptc="ADT";
						if(passenger.getAgeType().value().equalsIgnoreCase("infant")) {ptc = "INF";}
						if(passenger.getAgeType().value().equalsIgnoreCase("child")) {ptc = "CHD";}
						if(passenger.getAgeType().value().equalsIgnoreCase("adult")) {ptc = "ADT";}
					request.gotoParent()
					.addTag("PTC")
					.setText(ptc)
					.gotoParent()
					.addTag("GENDER")
					.setText(passenger.getGender() == Gender.MALE?"M":"F")
					.gotoParent()
					.addTag("EMAIL")
					.setText(passenger.getEmail())
					.gotoParent()
					.addTag("PHONE")
					.setText(passenger.getPhone());
				}	
				System.out.println("request" + request.toString());
				Source response = sendAndRecevie(request.toSource());
				System.out.println("response" + response.toString());
				String pnr = getPnrFromResponse(response);
				ReserveTicketResponse reserveTicketResponse = new ReserveTicketResponse();
				reserveTicketResponse.setReservationCode(pnr);
				return reserveTicketResponse;
			}

			private String getPnrFromResponse(Source response) {
				String pnr = "";
				try {
					pnr = XMLDoc.from(response, false).gotoChild("PNR").getText();
				} catch (XMLDocumentException e) {
					pnr = XMLDoc.from(response, false).gotoChild("ERROR").getText();
				}
				return pnr;
			}
		});
	}
	
	public PnrResponse pnrRequest(final PnrReq pnrReq) {
		return execute(new SessionCallBack<PnrResponse>() {

			@Override
			public PnrResponse doInSession(String terminalId) {
				XMLTag request = XMLDoc.newDocument(true).addRoot("AK_PNR")						
				.addTag("TERMINAL")
				.setText(terminalId)
				.gotoParent()
				.addTag("AGENT")
				.setText("LOSIFAJ.4104.410")
				.gotoParent()
				.addTag("IATA")
				.setText("LOSIFAJ")
				.gotoParent()
				.addTag("OFFICE")
				.setText("4104")
				.gotoParent()
				.addTag("ID")
				.setText("7201078856624851")
				.gotoParent()
				.addTag("LASTNAME")
				.setText(pnrReq.lastName)
				.gotoParent()
				.addTag("PNR")
				.setText(pnrReq.pnr);
				
				System.out.println("pnr-request" + request.toString());
				Source response = sendAndRecevie(request.toSource());
				System.out.println("pnr-response" + response.toString());
				
				PnrResponse pnrResponse = getPnrResponse(response);
				
				return pnrResponse;
			}

			private PnrResponse getPnrResponse(Source response) {
				final PnrResponse pnrResponse = new PnrResponse();
				pnrResponse.setPnr(XMLDoc.from(response, false).gotoChild("PNR").getAttribute("PNR_reference"));
				XMLDoc.from(response, false).gotoChild("PNR").forEach("NAME", new CallBack() {
					
					@Override
					public void execute(XMLTag doc) {
						pnrResponse.getTicket().add(extractTicket(doc));
					}

				});
//				XMLDoc.from(response, false).gotoRoot()
//						.forEach("AVAILABILITY/FLIGHT", new CallBack() {
//							public void execute(XMLTag doc) {
//								result.add(extractFlight(doc));
//							}
//						});
//				String ticketNumber = "";
//				try {
//					ticketNumber = XMLDoc.from(response, false).gotoChild("PNR").gotoFirstChild("NAME").gotoChild("TICKET").getAttribute("Ticket_number");
//				} catch (XMLDocumentException e) {
//				}
//				pnrResponse.setTicketNumber(ticketNumber);
				return pnrResponse;
			}
		});
	}
	
	public ConfirmTicketResponse confirmTicketRequest(final ConfirmReq confirmReq) {
		return execute(new SessionCallBack<ConfirmTicketResponse>() {

			@Override
			public ConfirmTicketResponse doInSession(String terminalId) {
				XMLTag request = XMLDoc.newDocument(true).addRoot("AK_PAY")						
				.addTag("TERMINAL")
				.setText(terminalId)
				.gotoParent()
				.addTag("HOST")
				.setText("AJ")
				.gotoParent()
				.addTag("IATA")
				.setText("LOSIFAJ")
				.gotoParent()
				.addTag("OFFICE")
				.setText("4104")
				.gotoParent()
				.addTag("ID")
				.setText("7201078856624851")
				.gotoParent()
				.addTag("PNR")
				.setText(confirmReq.pnr)
				.gotoParent()
				.addTag("FOP")
				.setText("DEMO")//TODO: confirmReq.fop
				.gotoParent()
				.addTag("PAY_AMOUNT")
				.setText(confirmReq.pay_amount)
				.gotoParent()
				.addTag("CURRENCY")
				.setText("NGN")
				.gotoParent()
				.addTag("CC_HOLDER_NAME")
				.setText(confirmReq.cardInfo.getCardHolderName())
				.gotoParent()
				.addTag("CC_TYPE")
				.setText("INVOICE")//TODO: confirmReq.cardInfo.getCardType()
				.gotoParent()
				.addTag("CC_NUMBER")
				.setText(confirmReq.cardInfo.getCardNumber())
				.gotoParent()
				.addTag("CC_EXP_MONTH")
				.setText(confirmReq.cardInfo.getCardExpMonth())
				.gotoParent()
				.addTag("CC_EXP_YEAR")
				.setText(confirmReq.cardInfo.getCardExpYear())
				.gotoParent()
				.addTag("CC_PIN")
				.setText(confirmReq.cardInfo.getCardPin())
				.gotoParent()
				.addTag("CC_SWITCH_ISSUE")
				.setText("")
				.gotoParent()
				.addTag("CC_EMAIL")
				.setText(confirmReq.cardInfo.getCardEmail())
				.gotoParent()
				.addTag("CC_FEE")
				.setText(confirmReq.cardInfo.getCardFee())
				.gotoParent()
				.addTag("SERVICE_FEE")
				.setText(confirmReq.cardInfo.getServiceFee());
				/*.gotoParent()
				.addTag("BILLING_ADDRESS1")
				.setText(confirmReq.cardInfo.getBillingAddress1())
				.gotoParent()
				.addTag("BILLING_ADDRESS2")
				.setText(confirmReq.cardInfo.getBillingAddress2())
				.gotoParent()
				.addTag("BILLING_CITY")
				.setText(confirmReq.cardInfo.getBillingCity())
				.gotoParent()
				.addTag("BILLING_ZIP")
				.setText(confirmReq.cardInfo.getBillingZip())
				.gotoParent()
				.addTag("BILLING_STATE")
				.setText(confirmReq.cardInfo.getBillingState())
				.gotoParent()
				.addTag("BILLING_COUNTRY")
				.setText(confirmReq.cardInfo.getBillingCountry());*/
				
				System.out.println("confirm-request" + request.toString());
				Source response = sendAndRecevie(request.toSource());
				System.out.println("confirm-response" + response.toString());
				
				ConfirmTicketResponse confirmResponse = getConfirmResponse(response, confirmReq.pnr);
				
				return confirmResponse;
			}

			private ConfirmTicketResponse getConfirmResponse(Source response, String pnr) {
				ConfirmTicketResponse confirmTicketResponse = new ConfirmTicketResponse();
				String success = XMLDoc.from(response, false).gotoChild("OK").getInnerText();
				confirmTicketResponse.setSucceeded(success.trim().equalsIgnoreCase("YES"));
				return confirmTicketResponse;
			}

		});
	}
	
	public ConfirmTicketResponse confirmTicketByVoucher(final ConfirmReq confirmReq) {
		return execute(new SessionCallBack<ConfirmTicketResponse>() {

			@Override
			public ConfirmTicketResponse doInSession(String terminalId) {
				XMLTag request = XMLDoc.newDocument(true)
				.addRoot("AK_PAY").addTag("TERMINAL").setText(terminalId)
				.gotoParent().addTag("HOST").setText("AJ")
				.gotoParent().addTag("IATA").setText("LOSIFAJ")
				.gotoParent().addTag("OFFICE").setText("4104")
				.gotoParent().addTag("ID").setText("7201078856624851")
				.gotoParent().addTag("PNR").setText(confirmReq.pnr)
			//	.gotoParent().addTag("FOP").setText("DEMO")//TODO: confirmReq.fop
				.gotoParent().addTag("PAY_AMOUNT").setText(confirmReq.pay_amount)
				.gotoParent().addTag("CURRENCY").setText("NGN")
				.gotoParent().addTag("CC_TYPE").setText("VOUCHER")
				.gotoParent().addTag("CC_NUMBER").setText(confirmReq.vouchers.get(0).getNumber())
				.gotoParent().addTag("CC_PIN").setText(confirmReq.vouchers.get(0).getPin());
				
				System.out.println("confirm-request" + request.toString());
				Source response = sendAndRecevie(request.toSource());
				System.out.println("confirm-response" + response.toString());
				
				ConfirmTicketResponse confirmResponse = getConfirmResponse(response, confirmReq.pnr);
				
				return confirmResponse;
			}

			private ConfirmTicketResponse getConfirmResponse(Source response, String pnr) {
				ConfirmTicketResponse confirmTicketResponse = new ConfirmTicketResponse();
				String success = XMLDoc.from(response, false).gotoChild("OK").getInnerText();
				confirmTicketResponse.setSucceeded(success.trim().equalsIgnoreCase("YES"));
				return confirmTicketResponse;
			}
		});
	}
	
	private Ticket extractTicket(XMLTag ticketNode) {
		Ticket ticket = new Ticket();
		ticket.setTicketNumber(ticketNode.gotoChild("TICKET").getAttribute("Ticket_number"));
		PassengerDetail passenger = new PassengerDetail();
		PersonName name = new PersonName();
		name.setFirstName(ticketNode.gotoParent().getAttribute("Pax_name"));
		passenger.setName(name);
		ticket.getPassenger().add(passenger);
		return ticket;
	}

	

	interface SessionCallBack<T> {
		T doInSession(String terminalId);
	}

	protected <T> T execute(SessionCallBack<T> sessionCallBack) {
		String terminalId = null;
		try {
			terminalId = startSession();
			return sessionCallBack.doInSession(terminalId);
		} finally {
			if (terminalId != null) {
				endSession(terminalId);
			}
		}
	}

	protected String startSession() {
		Source result = sendAndRecevie(XMLDoc.newDocument(true)
				.addRoot("AK_BSI").addTag("HOST").setText(host).gotoParent()
				.addTag("ID").setText(id).gotoParent().addTag("PASSWORD")
				.setText(password).toSource());

		return XMLDoc.from(result, true).gotoRoot().getText("TERMINAL");
	}

	protected void endSession(String terminal) {
		sendAndRecevie(XMLDoc.newDocument(true).addRoot("AK_BSO")
				.addTag("TERMINAL").setText(terminal).gotoParent()
				.addTag("HOST").setText(host).gotoParent().addTag("ID")
				.setText(id).toSource());
	}

	private Source sendAndRecevie(Source input) {
		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(aikioskServerUrl);
	//	System.out.println(aikioskServerUrl);

		try {
			StringWriter writer = new StringWriter();
			TransformerFactory.newInstance().newTransformer()
					.transform(input, new StreamResult(writer));
			String request = writer.toString();
			postMethod.setRequestHeader("Authorization",
					"Basic aW50ZXJnbG9iYWw6bmlnZXJpYQ==");
			postMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded");

			postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
					"XML", request) });

			if (LOGGER.isDebugEnabled()) {
//				LOGGER.debug("sending request");
//				LOGGER.debug(request);
			}

			int status = httpClient.executeMethod(postMethod);

			LOGGER.info("returned status " + status);

			if (status == HttpStatus.SC_OK) {
				Source response = new StringSource(postMethod
						.getResponseBodyAsString().trim());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(response.toString());
				}
				return response;
			} else {
				throw new RuntimeException("returned unexpected status "
						+ status);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			postMethod.releaseConnection();
		}
	}

	
	
	//For making PNR request from controller
	public PassengerNameRecord getPnrDetails(final String pnrRef) {
		final PassengerNameRecord pnr = new PassengerNameRecord();
		return execute(new SessionCallBack<PassengerNameRecord>() {

			@Override
			public PassengerNameRecord doInSession(String terminalId) {
				XMLTag request = XMLDoc.newDocument(true).addRoot("AK_PNR")						
				.addTag("TERMINAL")
				.setText(terminalId)
				.gotoParent()
				.addTag("AGENT")
				.setText("LOSIFAJ.4104.410")
				.gotoParent()
				.addTag("IATA")
				.setText("LOSIFAJ")
				.gotoParent()
				.addTag("OFFICE")
				.setText("4104")
				.gotoParent()
				.addTag("ID")
				.setText("7201078856624851")
				.gotoParent()
				.addTag("LASTNAME")
				.setText(pnr.getLastName()==null?"":pnr.getLastName())
				.gotoParent()
				.addTag("PNR")
				.setText(pnrRef);
				
				System.out.println("pnr-request" + request.toString());
				Source response = sendAndRecevie(request.toSource());
				//Source response = null;
 				//System.out.println("pnr-response" + response.toString());
				
				return parsePnrDetailsFromResponse(response,pnr);
			}

			private PassengerNameRecord parsePnrDetailsFromResponse(Source response,final PassengerNameRecord pnr) {
				XMLTag	pnrTag = XMLDoc.from(response, true).gotoRoot().gotoChild("PNR");
				pnr.setRef(pnrTag.findAttribute("PNR_reference"));
				pnrTag.forEach("NAME", new CallBack() {
					
					public void execute(XMLTag nameTag) {
						final Passenger passenger = new Passenger();
						passenger.setName(nameTag.findAttribute("Pax_name"));
						passenger.setPtc(nameTag.findAttribute("PTC"));
						nameTag.forEach("FLIGHT", new CallBack() {
							
							public void execute(XMLTag flightTag) {
								com.ikiosksng.airkiosk.hub.reservations.pnr.Flight flight = new com.ikiosksng.airkiosk.hub.reservations.pnr.Flight();
								flight.setAirline(flightTag.findAttribute("Airline"));
								flight.setFlightNumber(flightTag.findAttribute("Flight_number"));
								flight.setSegment(flightTag.findAttribute("Segment"));
								flight.setDate(flightTag.findAttribute("Date"));
								flight.setDeparture(flightTag.findAttribute("STD"));
								flight.setArrival(flightTag.findAttribute("STA"));
								flight.setClazz(flightTag.findAttribute("Class"));
								flight.setStatus(flightTag.findAttribute("Reservations_status"));
								flight.setSeats(flightTag.getAttribute("Seats"));
								passenger.addFlight(flight);
							}
						});
						nameTag.forEach("TICKET", new CallBack() {
							
							@Override
							public void execute(XMLTag ticketTag) {
								com.ikiosksng.airkiosk.hub.reservations.pnr.Ticket ticket = new com.ikiosksng.airkiosk.hub.reservations.pnr.Ticket();
								ticket.setNumber(ticketTag.findAttribute("Ticket_number"));
								ticket.setAgent(ticketTag.findAttribute("Agent_issued"));
								ticket.setDoi(ticketTag.findAttribute("Date_issued"));
								passenger.addTicket(ticket);
							}
						});
						if (nameTag.hasTag("PAY")){
							XMLTag payTag = nameTag.gotoChild("PAY");
							Payment payment = new Payment();
							payment.setFop(payTag.findAttribute("FOP"));
							payment.setCcType(payTag.findAttribute("CC_type"));
							payment.setAuthCode(payTag.findAttribute("Auth_code"));
							payment.setCurrency(payTag.findAttribute("Currency"));
							payment.setAmount(payTag.findAttribute("Amount"));
							payment.setAgtRef(payTag.findAttribute("AGT_REF"));
							payment.setCcFee(payTag.findAttribute("CC_fee"));
							payment.setServiceFee(payTag.findAttribute("SERVICEFEE"));
							payment.setAgent(payTag.findAttribute("Agent_paid"));
							passenger.setPayment(payment);
						}
						pnr.addPassenger(passenger);
					}
				});
				
				pnrTag.forEach("CONTACT", new CallBack() {
					@Override
					public void execute(XMLTag contactTag) {
						pnr.addContact(contactTag.findAttribute("CTC_details"));
					}
				});
		    	pnr.setAgent(pnrTag.gotoFirstChild("MODIFICATION").findAttribute("Agent"));
		    	pnr.setEquipment(pnrTag.gotoParent().gotoFirstChild("EQUIPMENT").findAttribute("Equipment"));
				return pnr;
			}
	
		});
	}
	
	
}
