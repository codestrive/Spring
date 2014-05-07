package com.ikiosksng.airkiosk.hub.terminal;

import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ikiosksng.airkiosk.hub.common.KeyedEntity;

@Entity
@Table(name="terminal")
public class Terminal extends KeyedEntity<Long> {

	private String deviceId;
	private String deviceKey;
	private boolean active;
	@Embedded
	private Location location;

	// For JPA
	Terminal() {
	}

	public static Terminal provisionNew(Location location) {
		String deviceId = UUID.randomUUID().toString();
		String deviceKey = UUID.randomUUID().toString();
		return new Terminal(deviceId, deviceKey, true, location);
	}

	Terminal(String deviceId, String deviceKey, boolean active,
			Location location) {
		this.deviceId = deviceId;
		this.deviceKey = deviceKey;
		this.active = active;
		this.location = location;
	}

	public boolean isAuthorized(String deviceId, String deviceKey) {
		if (this.deviceId.equals(deviceId) && this.deviceKey.equals(deviceKey)) {
			return active;
		} else {
			return false;
		}
	}

	public void activate() {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public boolean isActive() {
		return active;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "Terminal [deviceId=" + deviceId + ", deviceKey=" + deviceKey
				+ ", active=" + active + ", location=" + location + "]";
	}
	
	
}
