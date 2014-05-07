package com.ikiosksng.airkiosk.hub.reservations.pnr;

import java.util.ArrayList;
import java.util.List;

/*<NAME Pax_name="TEST1/IKIOSKMR/ADT" PTC="ADT" >
 <FLIGHT Airline="AJ" Flight_number="121" Segment="LOSABV" Date="3SEP2012" STD="06:45" STA="08:00" Class="V" Reservations_status="HK" Seats="1" />
 <FARE Flight="AJ121" Segment="LOSABV" Date="03SEP2012" Class="V" Net_fare="2909" Currency="NGN" Sub_charges="12871" >
 <CHARGES Amount="145.45" Charges_code="SA" />
 <CHARGES Amount="145.45" Charges_code="AP" />
 <CHARGES Amount="2500" Charges_code="PSC" />
 <CHARGES Amount="1450.10" Charges_code="AK" />
 <CHARGES Amount="1850" Charges_code="CU" />
 <CHARGES Amount="0" Charges_code="OL" />
 <CHARGES Amount="6580" Charges_code="YQ" />
 <CHARGES Amount="100" Charges_code="NA" />
 <CHARGES Amount="100" Charges_code="GU" />
 </FARE>
 </NAME>*/
public class Passenger {
	private String name;
	private String ptc;
	private List<Flight> flights;
	private Payment payment;
	private List<Ticket> tickets;
	
	
	public String getName() {
		return name;
	}

	public String getPtc() {
		return ptc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPtc(String ptc) {
		this.ptc = ptc;
	}

	public boolean addFlight(Flight e) {
		if (flights == null ) flights = new ArrayList<Flight>();
		return flights.add(e);
	}
	public List<Flight> getFlights() {
		return flights;
	}
	
	public void setPayment(Payment pay) {
		this.payment = pay;
	}
	public Payment getPayment() {
		return payment;
	}
	
	public List<Ticket> getTickets() {
		return tickets;
	}
	
	public void addTicket(Ticket ticket){
		if ( tickets == null ) tickets = new ArrayList<Ticket>();
		tickets.add(ticket);
	}
}
