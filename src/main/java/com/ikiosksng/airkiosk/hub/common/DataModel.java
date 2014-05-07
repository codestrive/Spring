package com.ikiosksng.airkiosk.hub.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DataModel {
	Map<String, Object> data = new HashMap<String, Object>();
	public Map<String, Object> getAsMap() {
		for(Field field: this.getClass().getFields()){
			try {
				data.put(field.getName(),field.get(this));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	

}
