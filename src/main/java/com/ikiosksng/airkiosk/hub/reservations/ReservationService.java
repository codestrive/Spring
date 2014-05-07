package com.ikiosksng.airkiosk.hub.reservations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
	
	public void recordReservation(Reservation reservation);
	
	public void confirmReservation(Reservation reservation);
	
	public Reservation findByPnr(String pnr);
	
	public Page<Reservation> findLatestReservations(String pnr, String status, String fromCode, String toCode, String flight, String jdate, int pageSize);
	
	public Page<PassengerInfo> findAllPassengers(Pageable pageable);

}
