package com.ikiosksng.airkiosk.hub.common;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class KeyedEntity<T extends Serializable> {

	@Id
	@GeneratedValue
	T id;

	public T getId() {
		return id;
	}
}
