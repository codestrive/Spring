package com.ikiosksng.airkiosk.hub.terminal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ikiosksng.airkiosk.hub.aero.AeroClient;
import com.ikiosksng.airkiosk.hub.reservations.ReservationService;
import com.ikiosksng.airkiosk.hub.reservations.pnr.PassengerNameRecord;

@Controller
public class ReservationsController {
  
 
  @Autowired
  ReservationService reservationService;
  
  @Autowired
  AeroClient aeroClient;
  
  @RequestMapping("/reservations/latest")
  public void latestReservations( @RequestParam(value="page",required=false,defaultValue="0")int page, 
		  						  @RequestParam(value="pnr",required=false)String pnr, @RequestParam(value="status",required=false)String status,
		  						  @RequestParam(value="fromCode",required=false)String fromCode, @RequestParam(value="flight",required=false)String flight,
		  						  @RequestParam(value="toCode",required=false)String toCode,@RequestParam(value="jdate",required=false)String jdate,Model model) {
	  model.addAttribute("page",reservationService.findLatestReservations(pnr,status,fromCode,toCode, flight,jdate,page));
 
  }
  
  @RequestMapping(value="/reservations/pnr",method=RequestMethod.GET)
  public String pnrRequest(){
	  
	  return "/reservations/test";
  }
  
  @RequestMapping(value="/reservations/pnr",method=RequestMethod.POST)
  public String pnrRequest(@RequestParam(value="pnr") String pnrRef,Model model){
	  PassengerNameRecord pnr = aeroClient.getPnrDetails(pnrRef);
	  model.addAttribute("pnr", pnr);
	  return "/reservations/test";
  }

}
