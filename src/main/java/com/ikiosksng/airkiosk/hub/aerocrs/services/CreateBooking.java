package com.ikiosksng.airkiosk.hub.aerocrs.services;

import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ikiosksng.airkiosk.hub.common.SendReceive;
import com.mycila.xmltool.XMLDoc;

public class CreateBooking {
	

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CreateBooking.class);
	
	
	public void getCreateBooking(){
		/*
		 * <?xml version="1.0" encoding="utf-8"?>
			<aerocrsapi>
			  <request>createBooking</request>
			  <connector>
			    <id>17C99F8D-6358-420C-A322-C71EA9592651</id>
			    <password>test</password>
			  </connector>
			  <parms>
			    <triptype>RT</triptype>
			    <adults>2</adults>
			    <child>0</child>
			  </parms>
			  <bookflight>
			    <fromcode>WIL</fromcode>
			    <tocode>ASV</tocode>
			    <flightid>271602</flightid>
			  </bookflight>
			  <bookflight>
			    <fromcode>ASV</fromcode>
			    <tocode>WIL</tocode>
			    <flightid>271818</flightid>
			  </bookflight>
			</aerocrsapi>
		*/
		System.out.println("getCreateBooking called");
		Source request = XMLDoc
				.newDocument(true)
				.addRoot("aerocrsapi")
				.addTag("request").setText("createBooking")
				.addTag("connector")
				.addTag("id").setText("E1C266A4-5747-4AD4-A036-F13D557DB2CA")
				.addTag("password").setText("asd9f9042").gotoParent()
				.addTag("parms")
				.addTag("triptype").setText("OW")
				.addTag("adults").setText("1")
				.addTag("child").setText("0").gotoParent()
				.addTag("bookflight")
				.addTag("fromcode").setText("WIL")
				.addTag("tocode").setText("MBA")
				.addTag("flightid").setText("1474237").gotoParent()
				.toSource();
		System.out.println("request -- "+request.toString());
		Source response = new SendReceive().sendAndRecevie(request);
		System.out.println("got response"+response);
	}
}
