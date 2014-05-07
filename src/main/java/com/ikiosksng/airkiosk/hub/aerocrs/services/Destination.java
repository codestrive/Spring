package com.ikiosksng.airkiosk.hub.aerocrs.services;

import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ikiosksng.airkiosk.hub.common.SendReceive;
import com.mycila.xmltool.CallBack;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;

public class Destination {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Destination.class);

public static class FlieghtDestination{
		
		String name,code,iatacode,icaocode,country;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getIatacode() {
			return iatacode;
		}

		public void setIatacode(String iatacode) {
			this.iatacode = iatacode;
		}

		public String getIcaocode() {
			return icaocode;
		}

		public void setIcaocode(String icaocode) {
			this.icaocode = icaocode;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
		
		@Override
		public String toString(){
			return "FlieghtDestination [ name :"+ name +
					" code :" + code +
					" iatacode :" + iatacode +
					" icaocode :" + icaocode +
					" country :" + country ;
		}
	}
	
		public FlieghtDestination getDestination(){
			/*
			<?xml version="1.0" encoding="utf-8"?>
			<aerocrsapi>
			  <request>getDestinations</request>
			  <connector>
			    <id>17C99F8D-6358-420C-A322-C71EA9592651</id>
			    <password>test</password>
			  </connector>
			</aerocrsapi>
			*/
		Source request = XMLDoc
				.newDocument(true)
				.addRoot("aerocrsapi")
				.addTag("request").setText("getDestinations")
				.addTag("connector")
				.addTag("id").setText("E1C266A4-5747-4AD4-A036-F13D557DB2CA")
				.addTag("password").setText("asd9f9042").toSource();
				
			System.out.println("request"+request.toString());
			Source response = new SendReceive().sendAndRecevie(request);
			System.out.println("got response");
			return parseGetDestinationResponse(response);
		}
		
		private FlieghtDestination parseGetDestinationResponse(Source response) {
			 FlieghtDestination flieghtDestination = new FlieghtDestination();
			 System.out.println("_____responce :"+ response.toString());
			 XMLTag	destinationsTag = XMLDoc.from(response, true).gotoRoot().gotoChild("destinations");
			 destinationsTag.forEach("destination", new CallBack() {
				 int i = 0;
					public void execute(XMLTag nameTag) {
						
						System.out.println("======"+i +
								"== Child count =="+ nameTag.getChildCount() +
								"== Tag Name =="+ nameTag.getCurrentTagName() +
								"== Attribute Names =="+ nameTag.getChildElement() +
								"== get attr ==" + nameTag.getChildElement().get(1));
						
						System.out.println("Name :" + nameTag.gotoChild("name").getText());
						System.out.println("code :" + nameTag.gotoParent().gotoChild("code").getText());
						System.out.println("iatacode :" + nameTag.gotoParent().gotoChild("iatacode").getText());
						System.out.println("icaocode :" + nameTag.gotoParent().gotoChild("icaocode").getText());
						System.out.println("country :" + nameTag.gotoParent().gotoChild("country").getText());
						i++;
					}
				});
			 return flieghtDestination;
		}
}
