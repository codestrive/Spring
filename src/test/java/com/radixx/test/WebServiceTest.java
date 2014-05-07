package com.radixx.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.dom4j.io.DocumentResult;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringSource;

import com.ikiosksng.airkiosk.hub.aero.AeroRadixxClient;
import com.ikiosksng.airkiosk.hub.aero.AeroRadixxClient.AirportRoute;
import com.ikiosksng.airkiosk.hub.aero.AeroRadixxClient.AirportRoutesReq;
import com.ikiosksng.airkiosk.hub.aero.BaseRadixxClient;
import com.ikiosksng.airkiosk.hub.aero.BaseRadixxClient.CarrierCode;
import com.ikiosksng.airkiosk.hub.aero.BaseRadixxClient.SecurityToken;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;

@ContextConfiguration({ "classpath:META-INF/spring/spring-ws.xml", "classpath:META-INF/spring/application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class WebServiceTest {

	@Autowired
	WebServiceTemplate webServiceTemplate;

	@Autowired
	private AeroRadixxClient aeroRadixxClient;

	@Test
	@Ignore
	public void authenticationTest() {

		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("ns4", "http://tempuri.org/")
		    .addRoot("ns4:RetrieveSecurityToken")
		    .addTag("ns4:RetrieveSecurityTokenRequest")
		    .addNamespace("ns2", "http://schemas.datacontract.org/2004/07/Radixx.ConnectPoint.Request")
		    .addTag("ns2:CarrierCodes")
		    .addTag("ns2:CarrierCode")
		    .addTag("ns2:AccessibleCarrierCode")
		    .setText("9J")
		    .gotoParent()
		    .gotoParent()
		    .addNamespace("ns3", "http://schemas.datacontract.org/2004/07/Radixx.ConnectPoint.Security.Request")
		    .addTag("ns3:LogonID")
		    .setText("FountegeTech_9J_Uat")
		    .addTag("ns3:Password")
		    .setText("F0untegeTech20130313");

		DocumentResult result = new DocumentResult();

		webServiceTemplate.sendSourceAndReceiveToResult(req.toSource(), new
		    SoapActionCallback(
		        "http://tempuri.org/IConnectPoint_Security/RetrieveSecurityToken"),
		    result);

		// System.out.println(req.toString());

		Source response = new StringSource(result.getDocument().asXML());

		// System.out.println(response.toString());
		System.out.println();
		System.out.println();
		System.out.println("_________________________________________");

		String securityToken = XMLDoc.from(response, true)
		    .addNamespace("a", "http://schemas.datacontract.org/2004/07/Radixx.ConnectPoint.Security.Response")
		    .gotoChild("RetrieveSecurityTokenResult")
		    .gotoChild("a:SecurityToken").getText();
		System.out.println("  security token -->" + securityToken);
		System.out.println();
	}

	@Test
	@Ignore
	public void test2() {
		List<CarrierCode> carrierCodesList = new ArrayList<AeroRadixxClient.CarrierCode>();
		CarrierCode carrierCode = new CarrierCode();
		carrierCode.setAccessibleCarrierCode("PB");
		carrierCodesList.add(carrierCode);
		CarrierCode carrierCode2 = new CarrierCode();
		carrierCode2.setAccessibleCarrierCode("9J");
		carrierCodesList.add(carrierCode2);
		aeroRadixxClient.prepareAuthenticationReq(carrierCodesList);
	}

	@Test
	public void getAirportRoutes() {

		AirportRoutesReq req = new AirportRoutesReq();
		req.setLanguageCode("en");
		SecurityToken securityToken = createSecurityToken();
		req.setSecurityToken(securityToken);
		List<AirportRoute> routes = aeroRadixxClient.getAirportRoutes(req);
		 System.out.println(">>>>>>>>>>>>>>>>>>>>>");
		for (AirportRoute airportRoute : routes) {
	    System.out.println(airportRoute);
		
    }
		
	}

	private SecurityToken createSecurityToken() {
		SecurityToken securityToken = new SecurityToken();
		List<CarrierCode> carrierCodes = new ArrayList<BaseRadixxClient.CarrierCode>();
		CarrierCode carrierCode = new CarrierCode();
		carrierCode.setAccessibleCarrierCode("9J");
		carrierCodes.add(carrierCode);
		securityToken.setCarrierCodes(carrierCodes);
		return securityToken;
	}
}
