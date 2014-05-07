package com.ikiosksng.airkiosk.hub.aerocrs.services;

import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ikiosksng.airkiosk.hub.aero.AeroCRSClient;
import com.ikiosksng.airkiosk.hub.common.SendReceive;
import com.mycila.xmltool.XMLDoc;

public class Flight {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Flight.class);
	
	public void getFlightDetails(){
		
		/*
		 * <?xml version="1.0" encoding="utf-8"?>
			<aerocrsapi>
			  <request>getFlights</request>
			  <connector>
			     <id>E1C266A4-5747-4AD4-A036-F13D557DB2CA</id>
			    <password>asd9f9042</password>
			  </connector>
			  <parms>
			    <triptype>RT</triptype>
			    <fromcode>WIL</fromcode>
			    <tocode>ASV</tocode>
			    <flightdate1>2014/04/14</flightdate1>
			    <flightdate2>2014/04/15</flightdate2>
			    <adults>2</adults>
			    <child>0</child>
			  </parms>
			</aerocrsapi>
		*/
		Source request = XMLDoc
				.newDocument(true)
				.addRoot("aerocrsapi")
				.addTag("request").setText("getFlights")
				.addTag("connector")
				.addTag("id").setText("E1C266A4-5747-4AD4-A036-F13D557DB2CA")
				.addTag("password").setText("asd9f9042").gotoParent()
				.addTag("parms")
				.addTag("triptype").setText("RT")
				.addTag("fromcode").setText("WIL")
				.addTag("tocode").setText("ASV")
				.addTag("flightdate1").setText("2014/04/14")
				.addTag("flightdate2").setText("2014/04/15")
				.addTag("adults").setText("2")
				.addTag("child").setText("0")
				.toSource();
		
		System.out.println("request"+request.toString());
		Source response = new SendReceive().sendAndRecevie(request);
		System.out.println("got response"+response);
				
		
	}

}
