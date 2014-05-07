package com.ikiosksng.airkiosk.hub.reservations.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.ikiosksng.airkiosk.hub.reservations.PassengerInfo;
import com.ikiosksng.airkiosk.hub.reservations.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> , QueryDslPredicateExecutor<Reservation> {

  @Query("select r from Reservation r order by r.mainItinerary.journeyDate desc")
  Page<Reservation> findLatestReservations(Pageable pageable);

  @Query("select r from Reservation r where r.pnr=:pnr")
  Reservation findByPnr(@Param("pnr")String pnr);
  
  @Query("select r from PassengerInfo r order by r.id")
  Page<PassengerInfo> findAllPassengers(Pageable pageable);

}
