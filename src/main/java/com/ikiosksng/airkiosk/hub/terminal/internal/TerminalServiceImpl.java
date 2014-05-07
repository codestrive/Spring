package com.ikiosksng.airkiosk.hub.terminal.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikiosksng.airkiosk.hub.terminal.Location;
import com.ikiosksng.airkiosk.hub.terminal.Terminal;
import com.ikiosksng.airkiosk.hub.terminal.TerminalService;

@Service("terminalService")
@Transactional
public class TerminalServiceImpl implements TerminalService {

	private Logger LOGGER = LoggerFactory.getLogger(TerminalServiceImpl.class);
	
	@Autowired
	TerminalRepository terminalRepository;
	
	@Override
	@Transactional(readOnly=true)
	public boolean isAuthorized(String deviceId, String deviceKey) {
		Terminal terminal = terminalRepository.findByDeviceId(deviceId);
		LOGGER.info("terminal for device id " + deviceId + " is " + terminal);
		return terminal != null ? terminal.isAuthorized(deviceId, deviceKey)  : false;
	}

	@Override
	public Terminal provisionNew(Location location) {
		Terminal terminal = Terminal.provisionNew(location);
		terminalRepository.save(terminal);
		return terminal;
	}

}
