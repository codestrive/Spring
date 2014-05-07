package com.ikiosksng.airkiosk.hub.terminal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

  @RequestMapping("/")
  public String index() {
    return "redirect:./reservations/latest";
  }
  
  @RequestMapping("/passengers/")
  public String passengers() {
	  return "redirect:./list";
  }
  
}
