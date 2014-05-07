package com.ikiosksng.airkiosk.hub.reservations.pnr;
//	<FLIGHT Airline="AJ" Flight_number="121" Segment="LOSABV" Date="3SEP2012" STD="06:45" STA="08:00" Class="V" Reservations_status="HK" Seats="1" />
public class Flight {
	private String airline;
	private String flightNumber;
	private String segment;
	private String date;
	private String departure;
	private String arrival;
	private String clazz;
	private String status;
	private String seats;
	public String getAirline() {
		return airline;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public String getSegment() {
		return segment;
	}
	public String getDate() {
		return date;
	}
	public String getDeparture() {
		return departure;
	}
	public String getArrival() {
		return arrival;
	}
	public String getClazz() {
		return clazz;
	}
	public String getStatus() {
		return status;
	}
	public String getSeats() {
		return seats;
	}
	public Flight setAirline(String airline) {
		this.airline = airline;
		return this;
	}
	public Flight setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
		return this;
	}
	public Flight setSegment(String segment) {
		this.segment = segment;
		return this;
	}
	public Flight setDate(String date) {
		this.date = date;
		return this;
	}
	public Flight setDeparture(String departure) {
		this.departure = departure;
		return this;
	}
	public Flight setArrival(String arrival) {
		this.arrival = arrival;
		return this;
	}
	public Flight setClazz(String clazz) {
		this.clazz = clazz;
		return this;
	}
	public Flight setStatus(String status) {
		this.status = status;
		return this;
	}
	public Flight setSeats(String seats) {
		this.seats = seats;
		return this;
	}
	
	
	

}
