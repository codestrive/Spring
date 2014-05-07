package com.ikiosksng.airkiosk.hub.reservations.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ikiosksng.airkiosk.hub.reservations.PassengerInfo;
import com.ikiosksng.airkiosk.hub.reservations.QReservation;
import com.ikiosksng.airkiosk.hub.reservations.Reservation;
import com.ikiosksng.airkiosk.hub.reservations.Reservation.Status;
import com.ikiosksng.airkiosk.hub.reservations.ReservationService;
import com.mysema.query.types.expr.BooleanExpression;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
	private static final int DEFAULT_PAGE_SIZE = 20;
	@Autowired
	ReservationRepository reservationRepository;

	@Override
	public void recordReservation(Reservation reservation) {
		reservationRepository.save(reservation);
	}

	@Override
	public void confirmReservation(Reservation reservation) {
		reservationRepository.save(reservation);
	}


	@Override
	public Reservation findByPnr(String pnr) {
		return reservationRepository.findByPnr(pnr);
	}

	@Override
	public Page<PassengerInfo> findAllPassengers(Pageable pageable) {
		return reservationRepository.findAllPassengers(pageable);
	}

	@Override
	public Page<Reservation> findLatestReservations(String pnr, String status,
			String fromCode, String toCode, String flight, String jdate,
			int page) {
		 Pageable pageable = new PageRequest(page,DEFAULT_PAGE_SIZE,Direction.DESC,"mainItinerary.journeyDate");
		 QReservation reservation = QReservation.reservation;
		 BooleanExpression predicate = null;
		 if ( StringUtils.hasLength(pnr) ) 
			 predicate = appendPredicate(predicate, reservation.pnr.like(pnr+"%"));
		 if (StringUtils.hasLength(status) )  
			 predicate = appendPredicate(predicate, reservation.status.eq(Status.valueOf(status)));
		 if (StringUtils.hasLength(fromCode) )  
			 predicate = appendPredicate(predicate, reservation.mainItinerary.from.equalsIgnoreCase(fromCode));
		 if (StringUtils.hasLength(toCode))  
			 predicate = appendPredicate(predicate, reservation.mainItinerary.to.equalsIgnoreCase(toCode));
		 if (StringUtils.hasLength(flight))  
			 predicate = appendPredicate(predicate, reservation.mainItinerary.flight.equalsIgnoreCase(flight));
		 if (predicate == null )		 						
			 return  reservationRepository.findAll(pageable);
		 else
			 return  reservationRepository.findAll(predicate,pageable);
	}

	private BooleanExpression appendPredicate(BooleanExpression first,
			BooleanExpression second) {
		if(first == null) 
			  return second;
		 else
			  return first.and(second);
	}
}
