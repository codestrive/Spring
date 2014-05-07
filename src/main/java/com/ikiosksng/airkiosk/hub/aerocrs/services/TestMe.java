package com.ikiosksng.airkiosk.hub.aerocrs.services;



public class TestMe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*XMLTag request = XMLDoc
				.newDocument(true)
				.addRoot("aerocrsapi")
				.addTag("request").setText("getDestinations")
				.addTag("connector")
				.addTag("id").setText("E1C266A4-5747-4AD4-A036-F13D557DB2CA")
				.addTag("password").setText("asd9f9042");
		
		System.out.println("request : \n"+request.toString());
		*/
		//Destination des = new Destination();
		//des.getDestination();
		
		//Flight flight = new Flight();
		//flight.getFlightDetails();
		
		// CreateBooking cb = new CreateBooking();
		// cb.getCreateBooking();
		
		ConfirmBooking confBooking = new ConfirmBooking();
		confBooking.getConfirmBooking();
	}
	
}
