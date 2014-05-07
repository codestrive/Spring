package com.ikiosksng.airkiosk.hub.aero;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.dom4j.io.DocumentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringSource;

import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;

public class BaseRadixxClient {

	/**
	 * reqNSURI = request namespace uri
	 * 
	 * resNSURI = response namespace uri
	 * 
	 * exNSURI = exception namespace uri
	 */

	@Value("${radixx.loginId}")
	protected String radixxLoginId;

	@Value("${radixx.password}")
	protected String radixxPassword;

	@Value("#{'${radixx.carrierCodes}'.split(',')}")
	protected List<String> carrierCodes;

	@Value("${connection.req.ns.uri}")
	protected String connectionReqNSURI;

	@Value("${connection.ex.ns.uri}")
	protected String connectionExNSURI;

	@Value("${security.req.ns.uri}")
	protected String securityReqNSURI;

	@Value("${security.res.ns.uri}")
	protected String securityResNSURI;

	@Value("${reservation.req.ns.uri}")
	protected String reservationReqNSURI;

	@Value("${reservation.res.ns.uri}")
	protected String reservationResNSURI;

	@Value("${flight.req.ns.uri}")
	protected String flightReqNSURI;

	@Value("${flight.res.ns.uri}")
	protected String flightResNSURI;

	@Value("${fees.req.ns.uri}")
	protected String feesReqNSURI;

	@Value("${travelAgent.req.ns.uri}")
	protected String travelAgentReqNSURI;

	@Value("${pricingFareQuote.req.ns.uri}")
	protected String pricingFareQuoteReqNSURI;

	@Value("${pricingService.req.ns.uri}")
	protected String pricingServiceReqNSURI;

	@Value("${fulfillment.ns.uri}")
	protected String fulfillmentNSURI;

	@Value("${serilization.ns.uri}")
	protected String serilizationNSURI;

	// Webservices Endpoints

	@Value("${radixx.security.ws.endpoint}")
	protected String securityEndpointURL;

	@Value("${radixx.flight.ws.endpoint}")
	protected String flightEndpointURL;

	// Soap actions

	@Value("${radixx.retrieve.retrieveSeatAvailabilityList.soapAction}")
	protected String retrieveSeatAvailabilitySA;

	@Value("${radixx.retrieve.airportRoute.soapAction}")
	protected String retrieveAirportRoutesSA;

	@Value("${radixx.retrieve.securityToken.soapAction}")
	protected String retrieveSecurityTokenSA;

	@Autowired
	protected WebServiceTemplate webServiceTemplate;

	public static class CarrierCode {
		private String accessibleCarrierCode;

		public String getAccessibleCarrierCode() {
			return accessibleCarrierCode;
		}

		public void setAccessibleCarrierCode(String accessibleCarrierCode) {
			this.accessibleCarrierCode = accessibleCarrierCode;
		}

	}

	public static class SecurityToken {
		private String securityGUID;
		private String clientIPAddress;
		private String historicUserName;
		private List<CarrierCode> carrierCodes;

		public String getSecurityGUID() {
			return securityGUID;
		}

		public void setSecurityGUID(String securityGUID) {
			this.securityGUID = securityGUID;
		}

		public String getClientIPAddress() {
			return clientIPAddress;
		}

		public void setClientIPAddress(String clientIPAddress) {
			this.clientIPAddress = clientIPAddress;
		}

		public String getHistoricUserName() {
			return historicUserName;
		}

		public void setHistoricUserName(String historicUserName) {
			this.historicUserName = historicUserName;
		}

		public List<CarrierCode> getCarrierCodes() {
			return carrierCodes;
		}

		public void setCarrierCodes(List<CarrierCode> carrierCodes) {
			this.carrierCodes = carrierCodes;
		}

	}

	public static class GenericSecurityToken {

		private SecurityToken securityToken;

		public SecurityToken getSecurityToken() {
			return securityToken;
		}

		public void setSecurityToken(SecurityToken securityToken) {
			this.securityToken = securityToken;
		}
	}

	/* Security authentication on service related request */

	public Source prepareAuthenticationReq(List<CarrierCode> carrierCodes) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("ns4", "http://tempuri.org/")
		    .addRoot("ns4:RetrieveSecurityToken")
		    .addTag("ns4:RetrieveSecurityTokenRequest");
		carrierCodesXMLTag(carrierCodes, req);
		req.addNamespace("ns3",
		    "http://schemas.datacontract.org/2004/07/Radixx.ConnectPoint.Security.Request")
		    .addTag("ns3:LogonID").setText(radixxLoginId)
		    .addTag("ns3:Password").setText(radixxPassword);
		System.out.println(req.toString());
		return req.toSource();
	}

	private void carrierCodesXMLTag(List<CarrierCode> carrierCodes, XMLTag req) {
		if (!carrierCodes.isEmpty()) {
			req.addNamespace("ns2", "http://schemas.datacontract.org/2004/07/Radixx.ConnectPoint.Request")
			    .addTag("ns2:CarrierCodes");
			for (CarrierCode carrierCode : carrierCodes) {
				if (carrierCode.getAccessibleCarrierCode() != null) {
					req.addTag("ns2:CarrierCode")
					    .addTag("ns2:AccessibleCarrierCode")
					    .setText(carrierCode.getAccessibleCarrierCode())
					    .gotoParent();
				}
			}
			req.gotoParent();
		}
	}

	protected void securityTokenXMLTag(SecurityToken securityToken, XMLTag req) {
		req.addNamespace("rad", connectionReqNSURI)
		    .addTag("rad:SecurityGUID")
		    .setText(securityToken.getSecurityGUID());

		if (!securityToken.getCarrierCodes().isEmpty()) {
			req.addNamespace("rad", connectionReqNSURI)
			    .addTag("rad:CarrierCodes");
			for (CarrierCode carrierCode : securityToken.getCarrierCodes()) {
				if (carrierCode.getAccessibleCarrierCode() != null) {
					req.addTag("rad:CarrierCode")
					    .addTag("rad:AccessibleCarrierCode")
					    .setText(carrierCode.getAccessibleCarrierCode())
					    .gotoParent();
				}

			}
			req.gotoParent();
		}
		if (securityToken.getClientIPAddress() != null) {
			req.addTag("rad:ClientIPAddress")
			    .setText(securityToken.getClientIPAddress());
		}
		if (securityToken.getHistoricUserName() != null) {
			req.addTag("rad:HistoricUserName")
			    .setText(securityToken.getHistoricUserName());
		}

	}

	interface SessionCallBack<T> {
		T doInSession(String securityToken);
	}

	protected <T> T execute(SessionCallBack<T> sessionCallBack) {
		String securityGUID = null;
		try {
			securityGUID = startSession();
			return sessionCallBack.doInSession(securityGUID);
		} finally {
			if (securityGUID != null) {
				endSession(securityGUID);
			}
		}
	}

	protected String startSession() {
		DocumentResult result = new DocumentResult();
		webserviceCall(prepareAuthenticationReq(getCarrierCodes(carrierCodes)), result, securityEndpointURL,
		    retrieveSecurityTokenSA);

		Source response = new StringSource(result.getDocument().asXML());

		String securityToken = XMLDoc.from(response, true)
		    .addNamespace("a", securityResNSURI)
		    .gotoChild("RetrieveSecurityTokenResult")
		    .gotoChild("a:SecurityToken").getText();

		return securityToken;
	}

	protected void endSession(String terminal) {

	}

	protected SecurityToken createSecurityToken(String securityGUID) {
		SecurityToken securityToken = new SecurityToken();
		/*
		 * List<CarrierCode> carrierCodes=new
		 * ArrayList<BaseRadixxClient.CarrierCode>(); CarrierCode carrierCode=new
		 * CarrierCode(); carrierCode.setAccessibleCarrierCode("9J");
		 * carrierCodes.add(carrierCode);
		 */
		securityToken.setCarrierCodes(getCarrierCodes(carrierCodes));
		securityToken.setSecurityGUID(securityGUID);
		return securityToken;
	}

	protected List<CarrierCode> getCarrierCodes(List<String> carrierCodes) {
		List<CarrierCode> list = new ArrayList<BaseRadixxClient.CarrierCode>();
		for (String string : carrierCodes) {
			CarrierCode carrierCode = new CarrierCode();
			carrierCode.setAccessibleCarrierCode(string);
			list.add(carrierCode);
		}
		return list;
	}

	protected void webserviceCall(final Source source, DocumentResult response, String endpointURL, String soapActionURI) {
		StopWatch sw = new StopWatch();
		sw.start(soapActionURI);
		webServiceTemplate.setDefaultUri(endpointURL);
		webServiceTemplate.sendSourceAndReceiveToResult(source, new
		    SoapActionCallback(
		        soapActionURI),
		    response);
		sw.stop();
		System.out.println(sw.shortSummary());
	}
}
