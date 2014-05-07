package com.ikiosksng.airkiosk.hub.terminal;

import javax.persistence.Embeddable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class Location {

	
	@NotEmpty(message="Address is not Empty")
	private String address;
    @NotEmpty(message="City is not Empty")
	private String city;
	@NotEmpty(message="State is not Empty")
	private String state;
	@NotEmpty(message="Country is not Empty")
	private String country;
	@NotEmpty(message="Post Code is not Empty")
	@Length(min = 5, max = 6, message = "{zip.length}")
	@Pattern(regexp = "[0-9]+" ,message="Only Numbers is allowed")
	private String postCode;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
