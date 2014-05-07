package codestirve.springMvc.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

	@RequestMapping(value="/koushik")
	public String myNameHandler(Locale locale, Model model){
		
		
		
		return "koushik";
	}
}
