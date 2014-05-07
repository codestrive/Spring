package com.ikiosksng.airkiosk.hub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ikioskng.airkiosks.ws.types.AgeType;
import com.ikioskng.airkiosks.ws.types.ConfirmTicketResponse;
import com.ikioskng.airkiosks.ws.types.Gender;
import com.ikioskng.airkiosks.ws.types.PassengerDetail;
import com.ikioskng.airkiosks.ws.types.PersonName;
import com.ikioskng.airkiosks.ws.types.PnrResponse;
import com.ikioskng.airkiosks.ws.types.ReserveTicketResponse;
import com.ikiosksng.airkiosk.hub.aero.AeroClient;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.AVReq;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.CardInfo;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.ConfirmReq;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.Flight;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.FlightDetails;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.PnrReq;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.ReserveReq;
import com.ikiosksng.airkiosk.hub.reservations.pnr.PassengerNameRecord;


@ContextConfiguration("classpath:META-INF/spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AeroClientTest {
	
	

	@Autowired
	AeroClient aeroClient;
	
	//@Test
	public void availabilityRequestTest() {
		
//		book for 7 days from today
		List<Flight> flights = aeroClient.availabilityRequest(new AVReq("ABV","LOS", new Date(System.currentTimeMillis()+86400*7*1000), "NGN"));
		System.out.println("Available Flights\n"+flights);
//		assertEquals(7,flights.size());
		assertTrue(flights.size()>0);
	}
	//@Test
	public void reserveTicketRequestTest() throws DatatypeConfigurationException {
		FlightDetails outbound = new FlightDetails();
			outbound.setDay(5);
			outbound.setFlightCode("AJ132");
			outbound.setFromAirportCode("ABV");
			outbound.setMonth("AUG");
			outbound.setToAirportCode("LOS");
			outbound.setYear(2012);
		List<PassengerDetail> passengers = new ArrayList<PassengerDetail>();
		PassengerDetail passenger = new PassengerDetail();
			passenger.setTitle("MR.");
			passenger.setAge((short) 26);
			passenger.setAgeType(AgeType.ADULT);
			GregorianCalendar calendar = new GregorianCalendar(1985, 4, 15);
			passenger.setDob(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			passenger.setEmail("mukul@tenxperts.com");
			passenger.setGender(Gender.MALE);
			PersonName name = new PersonName();
				name.setFirstName("Mukul");
				name.setLastName("Jain");
			passenger.setName(name);
			passenger.setPhone("924-303-4222");
		passengers.add(passenger);
		ReserveTicketResponse response = aeroClient.reserveTicketRequest(new ReserveReq("NGN",outbound, null, "P", "M",passengers, 1, 0, 0));
		
		String pnr = response.getReservationCode();
		System.out.println("PNR="+pnr);
		assertTrue(!pnr.equalsIgnoreCase(""));
		
//		PnrResponse pnrResponse = aeroClient.pnrRequest(new PnrReq("Jain", pnr));
//		assertNotNull(pnrResponse);
//		assertTrue(pnrResponse.getPnr().equalsIgnoreCase(pnr));
	}
	
	//@Test
	public void pnrRequestTest() {
		String pnr = "ZSRQSI";
		PnrResponse response = aeroClient.pnrRequest(new PnrReq("", pnr));
		assertNotNull(response);
		assertTrue(response.getPnr().equalsIgnoreCase(pnr));
	}
	
	//@Test
	public void confirmRequestTest() {
		
		CardInfo cardInfo = new CardInfo();
		cardInfo.setBillingAddress1("some address");
		cardInfo.setBillingAddress2("some other address");
		cardInfo.setBillingCity("Bangalore");
		cardInfo.setBillingState("KA");
		cardInfo.setBillingCountry("INDIA");
		cardInfo.setBillingZip("560072");
		cardInfo.setCardEmail("mukul@tenxperts.com");
		cardInfo.setCardExpMonth("09");
		cardInfo.setCardExpYear("2012");
		cardInfo.setCardFee("");
		cardInfo.setCardHolderName("MUKUL JAIN");
		cardInfo.setCardNumber("0000000000000000");
		cardInfo.setCardPin("222");
		cardInfo.setServiceFee("");
		cardInfo.setCardType("DEMO");
		ConfirmTicketResponse response = aeroClient.confirmTicketRequest(new ConfirmReq("ZSRQSI", "DEMO", "162.51", "GHC", cardInfo));
		System.out.println("--------------------------------");
		System.out.println(response.toString());
		assertNotNull(response);
	}
	//@Test
	public void pnrRetrival(){
		 
		PassengerNameRecord pnr = aeroClient.getPnrDetails("ZSSJLY");
		System.out.println("------------------------------------------------"+pnr.getPassengers());
	}
}
