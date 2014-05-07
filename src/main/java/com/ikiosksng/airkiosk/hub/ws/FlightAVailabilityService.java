package com.ikiosksng.airkiosk.hub.ws;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ikioskng.airkiosks.ws.types.AirportRef;
import com.ikioskng.airkiosks.ws.types.AvailabilitySearchRequest;
import com.ikioskng.airkiosks.ws.types.AvailabilitySearchResponse;
import com.ikioskng.airkiosks.ws.types.AvailabilitySearchResponse.Inbound;
import com.ikioskng.airkiosks.ws.types.AvailabilitySearchResponse.Outbound;
import com.ikioskng.airkiosks.ws.types.AvailableFlight;
import com.ikioskng.airkiosks.ws.types.Fare;
import com.ikiosksng.airkiosk.hub.aero.AeroClient;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.AVReq;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.Flight;

@Endpoint
public class FlightAVailabilityService {

	final Set<String> BUSINESS_CODES = new HashSet<String>(Arrays.asList(new String[]{"C","D","J"})) ;

	@Value("#{systemProperties.getProperty('mock',false)}")
	private boolean mock;

	@Autowired
	private AeroClient aeroClient;
	
	@PayloadRoot(namespace = ServiceConstants.NAME_SPACE_URI, localPart = "availability-search-request")
	public @ResponsePayload AvailabilitySearchResponse search(
			@RequestPayload AvailabilitySearchRequest availaibtySearchRequest) {
		if (isMockMode()) {
			return createMockResponse(availaibtySearchRequest);
		} else {
			
			AvailabilitySearchResponse availabilitySearchResponse = new AvailabilitySearchResponse();
			
			AVReq aReq = new AVReq(availaibtySearchRequest.getFrom().getCode(), 
					availaibtySearchRequest.getTo().getCode(), 
					availaibtySearchRequest.getDateOfJourney().toGregorianCalendar().getTime(),
					"NGN");
			availabilitySearchResponse.setOutbound(new Outbound());			
			for(Flight flight:aeroClient.availabilityRequest(aReq)) {
				availabilitySearchResponse
					.getOutbound()
					.getAvailableFlight()
					.add(convertToAvailableFlight(flight,availaibtySearchRequest));
			}
			System.out.println(availabilitySearchResponse.getOutbound().getAvailableFlight().size()+ " = flights list size");
			
			if (availaibtySearchRequest.isRoundTrip() != null && availaibtySearchRequest.isRoundTrip()) {
				aReq = new AVReq(availaibtySearchRequest.getTo().getCode(),
						availaibtySearchRequest.getFrom().getCode(),
						availaibtySearchRequest.getReturnDate().toGregorianCalendar().getTime(),
						"NGN");
				Inbound inbound = new Inbound();
				availabilitySearchResponse.setInbound(inbound);
				
				for(Flight flight:aeroClient.availabilityRequest(aReq)) {
					inbound.getAvailableFlight()
						.add(convertToAvailableFlight(flight,availaibtySearchRequest));
				}	
			}
			
			return availabilitySearchResponse;
		}
	}

	private AvailableFlight convertToAvailableFlight(Flight flight,AvailabilitySearchRequest request) {
		AvailableFlight availableFlight  = new AvailableFlight();
		availableFlight.setArrival(convertToXmlGeorgianCalendar(flight.getArrival()));
		availableFlight.setDeparture(convertToXmlGeorgianCalendar(flight.getDeparture()));
		availableFlight.setCode(flight.getFlightNumber());
		AirportRef from = new AirportRef();
		from.setCode(flight.getFromCode());
		availableFlight.setFrom(from);
		AirportRef to = new AirportRef();
		to.setCode(flight.getToCode());
		availableFlight.setTo(to);
		
		String flightClass = request.getFareType().value();
		System.out.println("flight class " + flightClass);
		AeroClient.Fare fare = getFareForClass(flightClass, flight.getFares()); // need to figure out which fare to use
		BigDecimal totalFare = new BigDecimal("0");
		if (fare != null) {
			totalFare = totalFare.add( fare.getAdultFare().multiply(new BigDecimal(request.getSeatsRequired().getAdults())));
			totalFare = totalFare.add( fare.getChildFare().multiply(new BigDecimal(request.getSeatsRequired().getChildren())));
			totalFare = totalFare.add( fare.getInfantFare().multiply(new BigDecimal(request.getSeatsRequired().getInfants())));
			availableFlight.getFare().add(createFare(totalFare,fare.getCurrency()));
			availableFlight.setClassCode(fare.getCode());
			return availableFlight;
		} else return null;
		
	}

	private AeroClient.Fare getFareForClass(String flightClass,	List<AeroClient.Fare> fares) {
		
		if (fares.isEmpty()) {
			return null;
		}
		AeroClient.Fare lowestFare = null;
		
		for (AeroClient.Fare fare : fares) {
			if (getFareClass(fare).equalsIgnoreCase(flightClass)) {
				if (lowestFare == null) {
					lowestFare = fare;
				} else {
					lowestFare = findLowest(fare,lowestFare);
				}
				
			}
		}
		return lowestFare;
	}
	
	private AeroClient.Fare findLowest(AeroClient.Fare first, AeroClient.Fare second) {
		if (first.getClassLevel()>second.getClassLevel()) {
			return second;
		} else {
			return first;
		}
	}

	String getFareClass(AeroClient.Fare fare) {
		if (BUSINESS_CODES.contains(fare.getCode())) {
			return "BUSINESS";
		} else if(fare.getCode().equalsIgnoreCase("X")) {
			return "";
		} else {
			return "ECONOMY";
		}
	}

	private XMLGregorianCalendar convertToXmlGeorgianCalendar(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		try {
			return DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException("error while converting date",e);
		}
	}

	private boolean isMockMode() {
			return mock;
	}

	private AvailabilitySearchResponse createMockResponse(
			AvailabilitySearchRequest availaibtySearchRequest) {
		AvailabilitySearchResponse response = new AvailabilitySearchResponse();

		response.setOutbound(new Outbound());
		response.getOutbound().getAvailableFlight().add(
				createAvailableFlight(availaibtySearchRequest,0,"40600","AJ121",
						createTime(availaibtySearchRequest.getDateOfJourney(), 6,50),
						createTime(availaibtySearchRequest.getDateOfJourney(), 8,10),false, "Z"));
		
		response.getOutbound().getAvailableFlight().add(
				createAvailableFlight(availaibtySearchRequest, 10, "21400", "AJ135",
						createTime(availaibtySearchRequest.getDateOfJourney(), 20, 30),
						createTime(availaibtySearchRequest.getDateOfJourney(), 21, 50),false, "V"));		
		
		response.getOutbound().getAvailableFlight().add(
				createAvailableFlight( availaibtySearchRequest, 10, "35000", "AJ133",
						createTime(availaibtySearchRequest.getDateOfJourney(), 15, 30),
						createTime(availaibtySearchRequest.getDateOfJourney(), 14, 50),false, "V"));		

		
		if (availaibtySearchRequest.isRoundTrip() != null && availaibtySearchRequest.isRoundTrip()) {
			Inbound inbound = new Inbound();
			inbound.getAvailableFlight().add( 
					createAvailableFlight( availaibtySearchRequest, 10, "15600", "AJ120",
							createTime(availaibtySearchRequest.getReturnDate(), 7, 00),
							createTime(availaibtySearchRequest.getReturnDate(), 8, 15),true, "V"));
			
			inbound.getAvailableFlight().add(
					createAvailableFlight( availaibtySearchRequest, 10, "15600", "AJ128",
							createTime(availaibtySearchRequest.getReturnDate(), 15, 45),
							createTime(availaibtySearchRequest.getReturnDate(), 17, 00),true, "V"));		
			
			inbound.getAvailableFlight().add(
					createAvailableFlight( availaibtySearchRequest, 10, "13600", "AJ136",
							createTime(availaibtySearchRequest.getReturnDate(), 20, 30),
							createTime(availaibtySearchRequest.getReturnDate(), 21, 45),true, "V"));		
			response.setInbound(inbound);
		}
		
		return response;
	}

	private AvailableFlight createAvailableFlight(
			AvailabilitySearchRequest availaibtySearchRequest,
			int availableSeats, String fare, String flightCode,
			XMLGregorianCalendar departure, XMLGregorianCalendar arrival,boolean outbound, String classCode) {
		AvailableFlight availableFlight = new AvailableFlight();
		
		if (!outbound) {
			availableFlight.setFrom(availaibtySearchRequest.getFrom());
			availableFlight.setTo(availaibtySearchRequest.getTo());
		} else {
			availableFlight.setTo(availaibtySearchRequest.getFrom());
			availableFlight.setFrom(availaibtySearchRequest.getTo());
		}
		availableFlight.setAvailability(availableSeats);
		availableFlight.getFare().add(createFare(fare, "NGN"));
		availableFlight.setDeparture(departure);
		availableFlight.setArrival(arrival);
		availableFlight.setCode(flightCode);
		availableFlight.setClassCode(classCode);
		return availableFlight;
	}

	private XMLGregorianCalendar createTime(
			XMLGregorianCalendar dateOfJournery, int hour, int min) {
		if (dateOfJournery == null) {
			try {
				dateOfJournery = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			} catch (DatatypeConfigurationException e) {
				return null;
			}
		}
		
		XMLGregorianCalendar time = (XMLGregorianCalendar) dateOfJournery
				.clone();
		time.setHour(hour);
		time.setMinute(min);
		time.setSecond(0);
		time.setMillisecond(0);
		return time;
	}
	
	private Fare createFare(String value, String currency) {
		return createFare(new BigDecimal(value), currency);
	}
	
	private Fare createFare(BigDecimal value, String currency) {
		Fare fare = new Fare();
		fare.setValue(value);
		fare.setCurrency(currency);
		return fare;
	}

}
