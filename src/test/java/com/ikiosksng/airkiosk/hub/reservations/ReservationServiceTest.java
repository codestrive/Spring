package com.ikiosksng.airkiosk.hub.reservations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ikiosksng.airkiosk.hub.reservations.Reservation.Status;
import com.ikiosksng.airkiosk.hub.ws.BookingService;


@ContextConfiguration("classpath:META-INF/spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationServiceTest {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	BookingService bookingService;
	
	@Test
	public void testForFindByPnr(){
		//Reservation findByPnr = reservationService.findByPnr("ZTAVRM");
		
		//System.out.println(findByPnr.getPassengers().get(0).geteMail());
		// BookOnHoldRequest bohRequest = new BookOnHoldRequest();
//		bohRequest.setPnr("ZTAVRM");
//		bohRequest.setAtmReference("2345232123");
//		bohRequest.setBohCharge("1000");
		// bookingService.bookFlightOnHold(bohRequest);
	}
	
	//@Test
	public void recordReservation(){
		
		Reservation reservation = new Reservation();
		reservation.setBaseFare(new BigDecimal(4));
		reservation.setBookingDate(new Date());
		reservation.setCardFee(new BigDecimal(5));
		reservation.setCurrency("INR");
		reservation.setPnr("ABCDEF");
		reservation.setServiceFee(new BigDecimal(5));
		reservation.setStatus(Status.CONFIRMED);
		ReservationItinerary main = new ReservationItinerary();
		main.setFlight("KFI001");
		main.setFrom("DEL");
		main.setTo("CHN");
		main.setJourneyDate(new Date());
		
		reservation.setMainItinerary(main);
		PassengerInfo info = new PassengerInfo();
		info.setAge("23");
		info.seteMail("selva@tenxperts.com");
		info.setPassengerName("Selva");
		info.setPhone("1234567890");
		List<PassengerInfo> list = new ArrayList<PassengerInfo>();
		list.add(info);
		
		reservation.setPassengers(list);
		reservationService.recordReservation(reservation);
	}
}
