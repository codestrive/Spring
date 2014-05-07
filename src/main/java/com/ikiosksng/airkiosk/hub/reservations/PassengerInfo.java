package com.ikiosksng.airkiosk.hub.reservations;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikiosksng.airkiosk.hub.common.KeyedEntity;

@Entity
@Table(name="passengerinfo")
public class PassengerInfo extends KeyedEntity<Long> {
	
	private String passengerName;
	private String eMail;
	private String phone;
	private String age;
	
	
	public String getPassengerName() {
		return passengerName;
	}
	public PassengerInfo setPassengerName(String passengerName) {
		this.passengerName = passengerName;
		return this;
	}
	public String geteMail() {
		return eMail;
	}
	public PassengerInfo seteMail(String eMail) {
		this.eMail = eMail;
		return this;
	}
	public String getPhone() {
		return phone;
	}
	public PassengerInfo setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public String getAge() {
		return age;
	}
	public PassengerInfo setAge(String age) {
		this.age = age;
		return this;
	}


}
