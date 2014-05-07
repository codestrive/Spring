package com.ikiosksng.airkiosk.hub.terminal.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ikiosksng.airkiosk.hub.terminal.Location;
import com.ikiosksng.airkiosk.hub.terminal.Terminal;
import com.ikiosksng.airkiosk.hub.terminal.TerminalService;
import com.ikiosksng.airkiosk.hub.terminal.internal.TerminalRepository;

@Controller
@RequestMapping("/terminal")
public class TerminalController {

	@Autowired
	TerminalService terminalService;

	@Autowired
	private TerminalRepository terminalRepository;

	@RequestMapping(value = "/list{page}", method = RequestMethod.GET)
	public @ModelAttribute("page") Page<Terminal> list(	@ModelAttribute("searchTerminal") SearchTerminal searchTerminal,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

		if (searchTerminal == null) {
			searchTerminal = new SearchTerminal();
		}
		// if (searchTerminal.getAddress() != null) {
		// if (!searchTerminal.getAddress().isEmpty()) {
		//
		// return terminalRepository.findByLocationAddress(searchTerminal
		// .getAddress(), new PageRequest(page - 1, 5));
		//
		// }
		// }
		// return terminalRepository.findAll(new PageRequest(page - 1, 5));

		return this.searchPage(searchTerminal, page);
	}

	@RequestMapping(value = "show/{id}", method = RequestMethod.GET)
	public String show(Model model, @PathVariable("id") long id) {
		model.addAttribute(terminalRepository.findOne(id));
		return "terminal/show";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String setupForNew(Model model) {
		Location location = new Location();
		model.addAttribute("location", location);
		return "terminal/create";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String provisionNew(Model model,
			@ModelAttribute("location") @Valid Location location,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "terminal/create";
		} else {
			Terminal terminal = terminalService.provisionNew(location);
			model.addAttribute(terminal);
			return "redirect:./show/" + terminal.getId();
		}
	}

	// raj designed methods

	public String updateProvision() {
		return null;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deleteProvision(@PathVariable("id") long id) {
		terminalRepository.delete(terminalRepository.findOne(id));

		return "redirect:../list";
	}

	@RequestMapping(value = "/activation", method = RequestMethod.GET)
	public String ActivationProvision(@RequestParam("id") Long id,
			@RequestParam("page") int pageNo,
			@RequestParam("link") String callfrom, Model model) {

		Terminal terminal = terminalRepository.findOne(id);
		if (terminal.isActive()) {
			terminal.deactivate();
		} else
			terminal.activate();
		if (callfrom.equals("search")) {
			List<Terminal> content = new ArrayList<Terminal>();
			content.add(terminal);
			model.addAttribute("page", new PageImpl<Terminal>(content,
					new PageRequest(pageNo - 1, 5), content.size()));
			model.addAttribute("link", "search");
			return "terminal/list";
		}

		model.addAttribute("page", pageNo);

		return "redirect:./list";
	}

	// @RequestMapping(value = "/search", method = RequestMethod.GET)
	// public String SearchProvision(
	// Model model,
	// @RequestParam("searchBy") String searchBy,
	// @RequestParam("searchingElement") String searchElement,
	// @RequestParam(value = "page", required = false, defaultValue = "1") int
	// pageNo) {
	// Page<Terminal> page = (Page<Terminal>) this.searchPage(searchBy,
	// searchElement, pageNo);
	// model.addAttribute("page", page);
	// model.addAttribute("link", "search");
	// return "terminal/list";
	// }

	private Page<Terminal> searchPage(SearchTerminal searchTerminal, int pageNo) {
		Terminal terminal = null;
		Page<Terminal> pages = null;
		if (searchTerminal.getDeviceId() == null
				&& searchTerminal.getAddress() == null
				&& searchTerminal.getCity() == null
				&& searchTerminal.getState() == null
				&& searchTerminal.getCountry() == null) {
			System.out.println("All field of searchTerminal are null");
			return terminalRepository.findAll(new PageRequest(pageNo - 1, 5));
		} else if (searchTerminal.getDeviceId().isEmpty()
				&& searchTerminal.getAddress().isEmpty()
				&& searchTerminal.getCity().isEmpty()
				&& searchTerminal.getState().isEmpty()
				&& searchTerminal.getCountry().isEmpty()) {

			System.out.println("All field of searchTerminal are empty");
			return terminalRepository.findAll(new PageRequest(pageNo - 1, 5));
		} else if (searchTerminal.getDeviceId() != null
				&& !searchTerminal.getDeviceId().isEmpty()) {
			terminal = terminalRepository.findByDeviceId(searchTerminal
					.getDeviceId());
			List<Terminal> list = new ArrayList<Terminal>();
			list.add(terminal);
			pages = new PageImpl<Terminal>(list);
			return pages;

		} else if (searchTerminal.getAddress() != null
				&& !searchTerminal.getAddress().isEmpty()) {
			System.out.println("By address");
			return terminalRepository.findByLocationAddress(searchTerminal
					.getAddress(), new PageRequest(pageNo - 1, 5));
		} else if (searchTerminal.getCity() != null
				&& !searchTerminal.getCity().isEmpty()) {
			System.out.println("By city");
			return terminalRepository.findByLocationCity(searchTerminal
					.getCity(), new PageRequest(pageNo - 1, 5));
		} else if (searchTerminal.getState() != null
				&& !searchTerminal.getState().isEmpty()) {
			System.out.println("By state");
			return terminalRepository.findByLocationState(searchTerminal
					.getState(), new PageRequest(pageNo - 1, 5));
		} else if (searchTerminal.getCountry() != null
				&& !searchTerminal.getCountry().isEmpty()) {
			System.out.println("By Country");
			return terminalRepository.findByLocationCountry(searchTerminal
					.getCountry(), new PageRequest(pageNo - 1, 5));
		}

		return pages;
	}

}
