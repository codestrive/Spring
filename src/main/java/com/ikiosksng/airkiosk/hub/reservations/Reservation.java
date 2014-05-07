package com.ikiosksng.airkiosk.hub.reservations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ikiosksng.airkiosk.hub.common.KeyedEntity;

@Entity
@Table(name="reservation")
public class Reservation extends KeyedEntity<Long> {

	public enum Status {
		RESERVED, CONFIRMED
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "main_itinerary_id")
	private ReservationItinerary mainItinerary;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "return_itinerary_id")
	private ReservationItinerary returnItinerary;
	
	

	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "reservationId")
	private List<PassengerInfo> passengers;

	private Date bookingDate;
	
	@Column(unique=true,nullable=false)
	private String pnr;
	private String deviceKey;
	private String deviceId;
	private String tickertNumber;
	private BigDecimal baseFare;
	private BigDecimal cardFee;
	private BigDecimal serviceFee;
	private String currency;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Date getBookingDate() {
		return bookingDate;
	}

	public Reservation setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
		return this;
	}

	public String getPnr() {
		return pnr;
	}

	public Reservation setPnr(String pnr) {
		this.pnr = pnr;
		return this;
	}
	public Reservation setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}
	public String getDeviceId() {
		return deviceId;
	}
	
	public Reservation setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
		return this;
	}
	public String getDeviceKey() {
		return deviceKey;
	}

	public ReservationItinerary getMainItinerary() {
		return mainItinerary;
	}

	public Reservation setMainItinerary(ReservationItinerary main) {
		this.mainItinerary = main;
		return this;
	}

	public ReservationItinerary getReturnItinerary() {
		return returnItinerary;
	}

	public Reservation setReturnItinerary(ReservationItinerary returnItinerary) {
		this.returnItinerary = returnItinerary;
		return this;
	}

	public Status getStatus() {
		return status;
	}

	public Reservation setStatus(Status status) {
		this.status = status;
		return this;
	}

	public String getTickertNumber() {
		return tickertNumber;
	}

	public void setTickertNumber(String tickertNumber) {
		this.tickertNumber = tickertNumber;
	}

	public BigDecimal getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(BigDecimal baseFare) {
		this.baseFare = baseFare;
	}

	public BigDecimal getCardFee() {
		return cardFee;
	}

	public void setCardFee(BigDecimal cardFee) {
		this.cardFee = cardFee;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setPassengers(List<PassengerInfo> passengers) {
		this.passengers = passengers;
	}

	public List<PassengerInfo> getPassengers() {
		return passengers;
	}

}
