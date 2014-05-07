package com.ikiosksng.airkiosk.hub.terminal;


public interface TerminalService {

	public boolean isAuthorized(String deviceId,String deviceKey);
	
	public Terminal provisionNew(Location location);
	
}
