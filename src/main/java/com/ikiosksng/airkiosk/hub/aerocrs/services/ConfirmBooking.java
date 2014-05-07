package com.ikiosksng.airkiosk.hub.aerocrs.services;

import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ikiosksng.airkiosk.hub.common.SendReceive;
import com.mycila.xmltool.XMLDoc;

public class ConfirmBooking {


	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfirmBooking.class);
	
	public void getConfirmBooking(){
		/*
		 * <?xml version="1.0" encoding="utf-8"?>
			<aerocrsapi>
			  <request>confirmBooking</request>
			  <connector>
			    <id>17C99F8D-6358-420C-A322-C71EA9592651</id>
			    <password>test</password>
			  </connector>
			  <parms>
			    <bookingid>3751078</bookingid>
			    <agentconfirmation>apiconnector</agentconfirmation>
			    <remarks>test only</remarks>
			  </parms>
			  <passenger>
			    <paxtitle>Mr.</paxtitle>
			    <firstname>John</firstname>
			    <lastname>Doe</lastname>
			    <paxage></paxage>
			  </passenger>
			  <passenger>
			    <paxtitle>Mrs.</paxtitle>
			    <firstname>An</firstname>
			    <lastname>Doe</lastname>
			    <paxage></paxage>
			  </passenger>
			</aerocrsapi>
		*/
		
		Source request = XMLDoc
				.newDocument(true)
				.addRoot("aerocrsapi")
				.addTag("request").setText("confirmBooking")
				.addTag("connector")
				.addTag("id").setText("E1C266A4-5747-4AD4-A036-F13D557DB2CA")
				.addTag("password").setText("asd9f9042").gotoParent()
				.addTag("parms")
				.addTag("bookingid").setText("21602050")
				.addTag("agentconfirmation").setText("apiconnector")
				.addTag("remarks").setText("Test Only").gotoParent()
				.addTag("passenger")
				.addTag("paxtitle").setText("Mr.")
				.addTag("firstname").setText("Koushik")
				.addTag("lastname").setText("Ghosh")
				.addTag("paxage").setText("24")
				.toSource();
		
		System.out.println("request"+request.toString());
		Source response = new SendReceive().sendAndRecevie(request);
		System.out.println("got response"+response);
	}
}
