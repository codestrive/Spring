package com.ikiosksng.airkiosk.hub.reservations.pnr;

import java.util.ArrayList;
import java.util.List;

public class PassengerNameRecord {

	private String ref;
	private String lastName;
	private List<Passenger> passengers;
	private List<String> contacts;
	private String agent;
	private String equipment;
	
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getAgent() {
		return agent;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getEquipment() {
		return equipment;
	}
	
	public void addContact(String contact){
		if ( contacts == null ) contacts = new ArrayList<String>();
		contacts.add(contact);
	}
	public List<String> getContacts() {
		return contacts;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getRef() {
		return ref;
	}
	 
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	
	public void addPassenger(Passenger passenger){
		if (passengers == null ) passengers = new ArrayList<Passenger>();
		passengers.add(passenger);
	}
}
