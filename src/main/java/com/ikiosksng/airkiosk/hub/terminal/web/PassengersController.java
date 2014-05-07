package com.ikiosksng.airkiosk.hub.terminal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ikiosksng.airkiosk.hub.reservations.ReservationService;

@Controller
public class PassengersController {

	private static final int DEFAULT_PAGE_SIZE = 25;
	@Autowired
	ReservationService reservationService;

	@RequestMapping("/passengers/list")
	public void passengersList(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			Model model) {
		model.addAttribute("page",
				reservationService.findAllPassengers(new PageRequest(page,
						DEFAULT_PAGE_SIZE)));
	}

}
