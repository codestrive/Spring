package com.ikiosksng.airkiosk.hub.reservations;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ikiosksng.airkiosk.hub.common.KeyedEntity;

@Entity
@Table(name="reservationitinerary")
public class ReservationItinerary extends KeyedEntity<Long> {

  @Column(name="source")
  private String from;
  @Column(name="dest")
  private String to;
  private String flight;
  private Date journeyDate;

  public String getFrom() {
    return from;
  }

  public ReservationItinerary setFrom(String from) {
    this.from = from;
    return this;
  }

  public String getTo() {
    return to;
  }

  public ReservationItinerary setTo(String to) {
    this.to = to;
    return this;
  }

  public String getFlight() {
    return flight;
  }

  public ReservationItinerary setFlight(String flight) {
    this.flight = flight;
    return this;
  }

  public Date getJourneyDate() {
    return journeyDate;
  }

  public ReservationItinerary setJourneyDate(Date journeyDate) {
    this.journeyDate = journeyDate;
    return this;
  }

}
