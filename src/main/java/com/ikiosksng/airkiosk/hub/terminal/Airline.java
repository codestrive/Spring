package com.ikiosksng.airkiosk.hub.terminal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ikiosksng.airkiosk.hub.common.KeyedEntity;

@Entity
@Table(name="airline")
public class Airline extends KeyedEntity<Long>{

	private String name;
	
	public Airline() {
	}
	
	public Airline(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
