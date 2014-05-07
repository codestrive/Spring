package com.ikiosksng.airkiosk.hub.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ikioskng.airkiosks.ws.types.AgeType;
import com.ikioskng.airkiosks.ws.types.AirportRef;
import com.ikioskng.airkiosks.ws.types.BohDetails;
import com.ikioskng.airkiosks.ws.types.BookOnHoldRequest;
import com.ikioskng.airkiosks.ws.types.BookOnHoldRequest.FareMatrix;
import com.ikioskng.airkiosks.ws.types.BookOnHoldRequest.Flights;
import com.ikioskng.airkiosks.ws.types.BookOnHoldRequest.Flights.Flight;
import com.ikioskng.airkiosks.ws.types.BookOnHoldResponse;
import com.ikioskng.airkiosks.ws.types.BookedItinerary;
import com.ikioskng.airkiosks.ws.types.ConfirmTicketByvoucherRequest;
import com.ikioskng.airkiosks.ws.types.ConfirmTicketRequest;
import com.ikioskng.airkiosks.ws.types.ConfirmTicketResponse;
import com.ikioskng.airkiosks.ws.types.Fare;
import com.ikioskng.airkiosks.ws.types.FlightRef;
import com.ikioskng.airkiosks.ws.types.Gender;
import com.ikioskng.airkiosks.ws.types.Itinerary;
import com.ikioskng.airkiosks.ws.types.PassengerDetail;
import com.ikioskng.airkiosks.ws.types.PersonName;
import com.ikioskng.airkiosks.ws.types.PnrRequest;
import com.ikioskng.airkiosks.ws.types.PnrResponse;
import com.ikioskng.airkiosks.ws.types.ReserveTicketRequest;
import com.ikioskng.airkiosks.ws.types.ReserveTicketResponse;
import com.ikioskng.airkiosks.ws.types.Ticket;
import com.ikioskng.airkiosks.ws.types.Voucher;
import com.ikiosksng.airkiosk.hub.aero.AeroClient;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.CardInfo;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.ConfirmReq;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.FlightDetails;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.PnrReq;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.ReserveReq;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.VoucherInfo;
import com.ikiosksng.airkiosk.hub.common.MailDataModel;
import com.ikiosksng.airkiosk.hub.common.MailDataModel.Journey;
import com.ikiosksng.airkiosk.hub.common.MailService;
import com.ikiosksng.airkiosk.hub.reservations.PassengerInfo;
import com.ikiosksng.airkiosk.hub.reservations.Reservation;
import com.ikiosksng.airkiosk.hub.reservations.ReservationItinerary;
import com.ikiosksng.airkiosk.hub.reservations.ReservationService;

@Endpoint
public class BookingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

	@Value("#{systemProperties.getProperty('mock',false)}")
	private boolean mock;

	@Autowired
	private AeroClient aeroClient;

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	MailService mailService;


	@PayloadRoot(namespace = ServiceConstants.NAME_SPACE_URI, localPart = "reserve-ticket-request")
	public @ResponsePayload
	ReserveTicketResponse reserve(@RequestPayload ReserveTicketRequest reserveTicketRequest) {
		if (isMockMode()) {
			return createMockReserveTicketResponse(reserveTicketRequest);
		} else {
			ReserveTicketResponse reserveTicketResponse = new ReserveTicketResponse();
			LOGGER.debug(reserveTicketRequest + "");
			FlightDetails outboundFlight = createFlightDetails(reserveTicketRequest.getMain());

			FlightDetails inboundFlight = null;
			String inboundClassCode = "";
			if (reserveTicketRequest.isRoundTrip()) {
				inboundFlight = createFlightDetails(reserveTicketRequest.getReturn());
				inboundClassCode = reserveTicketRequest.getReturn().getClassCode();
			}

			int numAdult = 0, numInfant = 0, numChild = 0;
			for (PassengerDetail passengerDetail : reserveTicketRequest.getPassenger()) {
				LOGGER.debug(passengerDetail.getAgeType().toString());

				if (passengerDetail.getAgeType().value().equalsIgnoreCase("adult")) {
					numAdult++;
				}
				if (passengerDetail.getAgeType().value().equalsIgnoreCase("child")) {
					numChild++;
				}
				if (passengerDetail.getAgeType().value().equalsIgnoreCase("infant")) {
					numInfant++;
				}
				LOGGER.debug(passengerDetail.getGender() + " - gender");
			}
			LOGGER.debug(reserveTicketRequest.getMain().getClassCode());
			ReserveReq reserveReq = new ReserveReq( reserveTicketRequest.getCurrency(), outboundFlight,
					inboundFlight, reserveTicketRequest.getMain() .getClassCode(), inboundClassCode,
					reserveTicketRequest.getPassenger(), numAdult, numChild, numInfant);

			reserveTicketResponse = aeroClient.reserveTicketRequest(reserveReq);
			reserveTicketResponse.setBohDetails(createBohDetails(reserveTicketRequest, reserveTicketResponse));
			recordReservation(reserveTicketRequest, reserveTicketResponse);
			
			return reserveTicketResponse;
		}
	}

	private void recordReservation(ReserveTicketRequest reserveTicketRequest,
			ReserveTicketResponse reserveTicketResponse) {
		Reservation reservation = new Reservation();
		reservation
				.setPnr(reserveTicketResponse.getReservationCode().trim())
				.setDeviceId(reserveTicketRequest.getDeviceId())
				.setDeviceKey(reserveTicketRequest.getDeviceKey())
				.setMainItinerary(
						createReservationItinerary(reserveTicketRequest
								.getMain()))
				.setStatus(Reservation.Status.RESERVED)
				.setPassengers(
						createPassengerInfo(reserveTicketRequest.getPassenger()));
		reservation.setCurrency(reserveTicketRequest.getCurrency());
		reservation.setBookingDate(new Date());
//		set return Itinerary only if return flight is available
		if(reserveTicketRequest.isRoundTrip()) {
			reservation.setReturnItinerary(
					createReservationItinerary(reserveTicketRequest
							.getReturn()));
		}
		reservationService.recordReservation(reservation);
	}

	private List<PassengerInfo> createPassengerInfo(
			List<PassengerDetail> passengers) {
		List<PassengerInfo> passengersList = new ArrayList<PassengerInfo>();
		for (PassengerDetail passenger : passengers) {
			passengersList.add(new PassengerInfo()
					.seteMail(passenger.getEmail())
					.setPhone(passenger.getPhone())
					.setPassengerName(
							passenger.getName().getFirstName() + " "
									+ passenger.getName().getLastName()));
		}
		return passengersList;
	}

	private ReservationItinerary createReservationItinerary(Itinerary itinerary) {
		if (itinerary == null) {
			LOGGER.debug("itinerary is null");
			return null;
		}
		return new ReservationItinerary()
				.setFlight(itinerary.getFlight().getCode())
				.setTo(itinerary.getTo().getCode())
				.setFrom(itinerary.getFrom().getCode())
				.setJourneyDate(
						itinerary.getDateOfJourney().toGregorianCalendar()
								.getTime());
	}

	private FlightDetails createFlightDetails(Itinerary itinerary) {
		// itinerary object here is the main or the return tag in the XML
		FlightDetails flightDetails = new FlightDetails();
		flightDetails.setFlightCode(itinerary.getFlight().getCode());
		flightDetails.setFromAirportCode(itinerary.getFrom().getCode());
		flightDetails.setToAirportCode(itinerary.getTo().getCode());
		flightDetails.setDay(itinerary.getDateOfJourney().getDay());
		flightDetails
				.setMonth(getMonth(itinerary.getDateOfJourney().getMonth()));
		flightDetails.setYear(itinerary.getDateOfJourney().getYear());
		return flightDetails;
	}

	@PayloadRoot(namespace = ServiceConstants.NAME_SPACE_URI, localPart = "confirm-ticket-request")
	public @ResponsePayload
	ConfirmTicketResponse confirm(
			@RequestPayload ConfirmTicketRequest confirmTicketRequest) {
		// AK_PAY
		if (isMockMode()) {
			return createMockConfirmTicketResponse();
		} else {
			CardInfo cardInfo = createCardInfo(confirmTicketRequest);
			ConfirmReq confirmReq = new ConfirmReq(
					confirmTicketRequest.getReservationCode(),
					confirmTicketRequest.getFormOfPayment(),
					confirmTicketRequest.getPayAmount(),
					confirmTicketRequest.getCurrency(), cardInfo);

			ConfirmTicketResponse confirmTicketResponse = new ConfirmTicketResponse();
			confirmTicketResponse = aeroClient.confirmTicketRequest(confirmReq);

			if (confirmTicketResponse.isSucceeded()) {
				System.out.println("success");
				reservationService
						.confirmReservation(createConfirmedReservation(
								confirmTicketRequest, cardInfo,
								confirmTicketResponse));
			}

			return confirmTicketResponse;
		}

	}

	@PayloadRoot(namespace = ServiceConstants.NAME_SPACE_URI, localPart = "confirm-ticket-byvoucher-request")
	public @ResponsePayload ConfirmTicketResponse confirmTicketByVoucher( @RequestPayload ConfirmTicketByvoucherRequest request) {
		// AK_PAY
		if (isMockMode()) {
			return createMockConfirmTicketResponse();
		} else {
			List<VoucherInfo> list = getVoucherInfoFromRequest(request);
			ConfirmReq confirmReq = new ConfirmReq( request.getReservationCode(), request.getPayAmount(), list);

			ConfirmTicketResponse confirmTicketResponse = new ConfirmTicketResponse();
			confirmTicketResponse = aeroClient.confirmTicketByVoucher(confirmReq);

			if (confirmTicketResponse.isSucceeded()) {
				System.out.println("success");
				reservationService .confirmReservation(createConfirmedReservation( request, list, confirmTicketResponse));
			}

			return confirmTicketResponse;
		}

	}
	

	private List<VoucherInfo> getVoucherInfoFromRequest( ConfirmTicketByvoucherRequest request){
		List<Voucher> vouchers = request.getVoucher();
		List<VoucherInfo> vouchersInfo = new ArrayList<AeroClient.VoucherInfo>();
		for (Voucher voucher : vouchers){
			vouchersInfo.add(new VoucherInfo(voucher.getNumber(), voucher.getPin()));
		}
		return vouchersInfo;
	}

	protected Reservation createConfirmedReservation(
			ConfirmTicketRequest confirmTicketRequest, CardInfo cardInfo,
			ConfirmTicketResponse confirmTicketResponse) {
		Reservation reservation = reservationService
				.findByPnr(confirmTicketRequest.getReservationCode());
		reservation.setStatus(Reservation.Status.CONFIRMED)
					.setTickertNumber(confirmTicketResponse.getTicket());
		reservation.setBaseFare(new BigDecimal(confirmTicketRequest.getPayAmount()));
		reservation.setCardFee(new BigDecimal(cardInfo.getCardFee()));
		reservation.setServiceFee(new BigDecimal(cardInfo.getServiceFee()));
		reservation.setCurrency(confirmTicketRequest.getCurrency());
		return reservation;
	}

	private Reservation createConfirmedReservation( ConfirmTicketByvoucherRequest request, List<VoucherInfo> list, ConfirmTicketResponse confirmTicketResponse) {
		Reservation reservation = reservationService.findByPnr(request.getReservationCode());
		reservation.setStatus(Reservation.Status.CONFIRMED).setTickertNumber(confirmTicketResponse.getTicket());
		reservation.setBaseFare(new BigDecimal(request.getPayAmount()));
		return reservation;
	}
	
	private CardInfo createCardInfo(ConfirmTicketRequest confirmTicketRequest) {
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardNumber(confirmTicketRequest.getPaymentDetails()
				.getCcNumber());
		cardInfo.setCardHolderName(confirmTicketRequest.getPaymentDetails()
				.getNameOnCard());
		cardInfo.setCardExpMonth(confirmTicketRequest.getPaymentDetails()
				.getExpiryMonth() + "");
		cardInfo.setCardExpYear(confirmTicketRequest.getPaymentDetails()
				.getExpiryYear() + "");
		cardInfo.setCardPin(confirmTicketRequest.getPaymentDetails().getPin()
				+ "");
		cardInfo.setCardFee(confirmTicketRequest.getPaymentDetails()
				.getCardFee());
		cardInfo.setCardEmail(confirmTicketRequest.getCCEmail());
		cardInfo.setCardType(confirmTicketRequest.getPaymentDetails().getCcType()); //TODO need to get all the codes.
		cardInfo.setServiceFee(confirmTicketRequest.getPaymentDetails()
				.getServiceFee());
		return cardInfo;
	}

	@PayloadRoot(namespace = ServiceConstants.NAME_SPACE_URI, localPart = "pnr-request")
	public @ResponsePayload
	PnrResponse pnr(@RequestPayload PnrRequest pnrRequest) {
		if (isMockMode()) {
			return createMockPnrResponse(pnrRequest);
		} else {

			PnrReq pnrReq = new PnrReq(pnrRequest.getLastName(),
					pnrRequest.getPnr());

			PnrResponse pnrResponse = new PnrResponse();
			pnrResponse = aeroClient.pnrRequest(pnrReq);
			return pnrResponse;
		}
	}

	private PnrResponse createMockPnrResponse(PnrRequest pnrRequest) {
		PnrResponse response = new PnrResponse();
		response.setPnr("MOCKPNR");
		return response;
	}

	private ReserveTicketResponse createMockReserveTicketResponse(
			ReserveTicketRequest request) {
		ReserveTicketResponse response = new ReserveTicketResponse();
		String pnr  =createRandomPNR();
		response.setReservationCode(pnr.trim());
		response.setBohDetails(createBohDetails(request,response));
		return response;
	}
	private BohDetails createBohDetails(ReserveTicketRequest request,ReserveTicketResponse response) {
		String reservationCode = response.getReservationCode();
		BohDetails bohDetails = new BohDetails();
		XMLGregorianCalendar dateOfJourney = request.getMain().getDateOfJourney();
		String departureTime = request.getMain().getDepartureTime();
		long hoursToGo = hoursToGo(dateOfJourney.toString()+departureTime);
		bohDetails.setHoursToGo(hoursToGo);
		bohDetails.setAvailable(true);
		if ( hoursToGo < 8 ){
			bohDetails.setAvailable(false);
		}else if ( hoursToGo <= 24){
			bohDetails.setBohCharge(createFare(new BigDecimal(1000)));
			bohDetails.setAtmReference(createATMReference(reservationCode.trim()));
		}else if ( hoursToGo <= 48 ){
			bohDetails.setBohCharge(createFare(new BigDecimal(750)));
			bohDetails.setAtmReference(createATMReference(reservationCode.trim()));
		}else{
			bohDetails.setBohCharge(createFare(new BigDecimal(0)));
			bohDetails.setAtmReference(createATMReference(reservationCode.trim()));
		}
		return bohDetails;
	}

	
	private Fare createFare(BigDecimal value) {
		Fare fare = new Fare();
		fare.setCurrency("NGN");
		fare.setValue(value);
		return fare;
	}

	private  long hoursToGo(String strDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm");
		format.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
		Date date;
		try {
			date = format.parse(strDate);
			return (date.getTime() - (new Date()).getTime())  / (1000*60*60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	private String createRandomPNR(){
		String pnr="";
		int num;
		for(int i=0 ; i < 6; i++){
			num = new java.util.Random().nextInt(26);
			pnr = pnr + ((char)(num+65));
		}
		return "\n"+pnr+"\n";
	}

	private String createATMReference(String pnr){
		if ( pnr.equalsIgnoreCase("NO PNR"))
			return "NONE";
		int num;
		String ref ="";
		for(int i=0;i<6;i++){
		num = ((int)pnr.charAt(i))-64;
		if (num <10 ) ref = ref +"0";
		ref = ref + String.valueOf(num);
		}
		return ref;
	}
	private ConfirmTicketResponse createMockConfirmTicketResponse() {
		ConfirmTicketResponse confirmTicketResponse = new ConfirmTicketResponse();

		Ticket ticket = new Ticket();
		ticket.setTicketNumber("TKT" + new Random().nextInt());

		BookedItinerary bookedItinerary = new BookedItinerary();
		bookedItinerary.setDateOfJourney(createDate(new Date(), 10));
		bookedItinerary.setFrom(createAirportRef("BLR"));
		bookedItinerary.setTo(createAirportRef("MUM"));
		bookedItinerary.setFlight(createFlightRef("9W-952"));
		bookedItinerary.setPnr("9W" + new Random().nextInt());

		ticket.setMain(bookedItinerary);

		PassengerDetail passengerDetail = new PassengerDetail();
		passengerDetail.setAge((short) 35);
		passengerDetail.setAgeType(AgeType.ADULT);
		passengerDetail.setGender(Gender.MALE);
		passengerDetail.setName(createPersonName("Chuck", "Norris"));
		passengerDetail.setTitle("Mr.");

		ticket.getPassenger().add(passengerDetail);

		// confirmTicketResponse.setTicket(ticket);
		confirmTicketResponse.setSucceeded(true);
		return confirmTicketResponse;
	}

	private PersonName createPersonName(String firstName, String lastName) {
		PersonName personName = new PersonName();
		personName.setFirstName(firstName);
		personName.setLastName(lastName);
		return personName;
	}

	private FlightRef createFlightRef(String code) {
		FlightRef flightRef = new FlightRef();
		flightRef.setCode(code);
		return flightRef;
	}

	private AirportRef createAirportRef(String code) {
		AirportRef airportRef = new AirportRef();
		airportRef.setCode(code);
		return airportRef;
	}

	private XMLGregorianCalendar createDate(Date date, int days) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// should not happen
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * This request to book the flight on hold // essentially sending mail to passenger
	 * 
	 */
	@PayloadRoot(namespace = ServiceConstants.NAME_SPACE_URI, localPart = "book-on-hold-request")
	public @ResponsePayload BookOnHoldResponse bookFlightOnHold(@RequestPayload BookOnHoldRequest bohRequest) {
		LOGGER.debug("book on hold");

		MailDataModel mail = new MailDataModel();
		String pnr = bohRequest.getPNR();
		mail.setPnr(pnr);
		String atmRef = bohRequest.getATMRef();
		mail.setAtmReference(atmRef);
		String name = bohRequest.getName();
		mail.setName(name);
		String email = bohRequest.getEmail();
		FareMatrix fareMatrix = bohRequest.getFareMatrix();
		double bohFees = Double.valueOf(fareMatrix.getBohFees());
		mail.setBohFees(bohFees);
		BigInteger adultCount = fareMatrix.getAdult().getCount();
		mail.setAdultCount(adultCount.intValue());
		BigInteger childCount = fareMatrix.getChild().getCount();
		mail.setChildCount(childCount.intValue());
		BigInteger infantCount = fareMatrix.getInfant().getCount();
		mail.setInfantCount(infantCount.intValue());
		double baseFare = fareMatrix.getAdult().getFare();
		mail.setBaseFare(baseFare);
		List<Flight> flights = bohRequest.getFlights().getFlight();
		Flight flight = flights.get(0);
		Journey outbound = new MailDataModel.Journey();
		outbound.setDate(flight.getDate());
		outbound.setFcode(flight.getFcode());
		outbound.setFrom(flight.getFrom());
		outbound.setTo(flight.getTo());
		mail.setOutbound(outbound);
		if (flights.size()==2){
			flight = flights.get(1);
			Journey inbound = new MailDataModel.Journey();
			inbound.setDate(flight.getDate());
			inbound.setFcode(flight.getFcode());
			inbound.setFrom(flight.getFrom());
			inbound.setTo(flight.getTo());
			mail.setInbound(inbound);
		}else {
			mail.setInbound(null);
		}
		
		BookOnHoldResponse response =  new BookOnHoldResponse();
		if (mailService.sendMail(mail, email))
			response.setStatus("OK");
		else
			response.setStatus("ERROR");
		return response;
	}

	private boolean isMockMode() {
		return mock;
	}

	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month - 1];
	}

}
