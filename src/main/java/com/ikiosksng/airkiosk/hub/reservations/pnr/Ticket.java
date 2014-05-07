package com.ikiosksng.airkiosk.hub.reservations.pnr;

//<TICKET Ticket_number="9140006196230" Agent_issued="BOSRCAJ.4104.7201078856624851.12080412024515403" Date_issued="120804120227" />

public class Ticket {
	private String number;
	private String doi;
	private String agent;
	public String getNumber() {
		return number;
	}
	public String getDoi() {
		return doi;
	}
	public String getAgent() {
		return agent;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	
}
