package com.ikiosksng.airkiosk.hub.terminal.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/terminal")
public class LoginLogoutController {

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(@RequestParam(value="error", required=false) boolean error,Model model){
		if (error == true) {
			// Assign an error message
			model.addAttribute("error", "*  You have entered an invalid username or password!");
		} else {
			model.addAttribute("error", "");
		}
		
		return "terminal/login";
	}
}
