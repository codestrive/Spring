package com.ikiosksng.airkiosk.hub;

import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.xml.transform.StringSource;

import com.ikioskng.airkiosks.ws.types.PnrResponse;
import com.ikiosksng.airkiosk.hub.aero.AeroClient.PnrReq;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;

public class AeroClientDemo {
	private static final Logger LOGGER = LoggerFactory .getLogger(AeroClientDemo.class);
	
	private String password="interglos";
	private String id="4104";
	private String host="AJ";
	private String aikioskServerUrl="http://sutra106.airkiosk.com/cgi-bin/airkiosk/akapi_client";

	@Test
	public void logTest(){
		LOGGER.debug("Testing log");
	}
	//@Test
	public void payByVoucherTest(){
		execute(new SessionCallBack<String>() {

			@Override
			public String doInSession(String terminalId) {
				XMLTag request = XMLDoc.newDocument(true)
						.addRoot("AK_PAY").addTag("TERMINAL").setText(terminalId)
						.gotoParent().addTag("HOST").setText("AJ")
						.gotoParent().addTag("IATA").setText("LOSIFAJ")
						.gotoParent().addTag("OFFICE").setText("4104")
						.gotoParent().addTag("ID").setText("7201078856624851")
						.gotoParent().addTag("PNR").setText("ZTBCMY")
						.gotoParent().addTag("FOP").setText("CC")
						.gotoParent().addTag("PAY_AMOUNT").setText("13280")
						.gotoParent().addTag("CURRENCY").setText("NGN")
						.gotoParent().addTag("CC_HOLDER_NAME").setText("")
						.gotoParent().addTag("CC_TYPE").setText("VOUCHER")
						.gotoParent().addTag("CC_NUMBER").setText("2118012568126536")
						.gotoParent().addTag("CC_EXP_MONTH").setText("")
						.gotoParent().addTag("CC_EXP_YEAR").setText("")
						.gotoParent().addTag("CC_PIN").setText("0653")
						.gotoParent().addTag("CC_SWITCH_ISSUE").setText("")
						.gotoParent().addTag("CC_EMAIL").setText("")
						.gotoParent().addTag("CC_FEE").setText("") 
						.gotoParent().addTag("SERVICE_FEE").setText("");
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(request.toString());
				}

				Source response = sendAndRecevie(request.toSource());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(response.toString());
				}
				return null;
			}
		});
	}
	
	@Test
	public void pnrRequest() {
		 execute(new SessionCallBack<PnrResponse>() {

			@Override
			public PnrResponse doInSession(String terminalId) {
				XMLTag request = XMLDoc.newDocument(true).addRoot("AK_PNR")						
				.addTag("TERMINAL")
				.setText(terminalId)
				.gotoParent()
				.addTag("AGENT")
				.setText("LOSIFAJ.4104.410")
				.gotoParent()
				.addTag("IATA")
				.setText("LOSIFAJ")
				.gotoParent()
				.addTag("OFFICE")
				.setText("4104")
				.gotoParent()
				.addTag("ID")
				.setText("7201078856624851")
				.gotoParent()
				.addTag("LASTNAME")
				.setText("")
				.gotoParent()
				.addTag("PNR")
				.setText("ZTBCMY");
				
				System.out.println("pnr-request" + request.toString());
				Source response = sendAndRecevie(request.toSource());
				System.out.println("pnr-response" + response.toString());
				
				PnrResponse pnrResponse = getPnrResponse(response);
				
				return pnrResponse;
			}

			private PnrResponse getPnrResponse(Source response) {
//				final PnrResponse pnrResponse = new PnrResponse();
//				pnrResponse.setPnr(XMLDoc.from(response, false).gotoChild("PNR").getAttribute("PNR_reference"));
//				XMLDoc.from(response, false).gotoChild("PNR").forEach("NAME", new CallBack() {
//					
//					@Override
//					public void execute(XMLTag doc) {
//						pnrResponse.getTicket().add(extractTicket(doc));
//					}
//
//				});
//				XMLDoc.from(response, false).gotoRoot()
//						.forEach("AVAILABILITY/FLIGHT", new CallBack() {
//							public void execute(XMLTag doc) {
//								result.add(extractFlight(doc));
//							}
//						});
//				String ticketNumber = "";
//				try {
//					ticketNumber = XMLDoc.from(response, false).gotoChild("PNR").gotoFirstChild("NAME").gotoChild("TICKET").getAttribute("Ticket_number");
//				} catch (XMLDocumentException e) {
//				}
//				pnrResponse.setTicketNumber(ticketNumber);
				return null;
			}
		});
	}
	
	interface SessionCallBack<T> {
		T doInSession(String terminalId);
	}

	protected <T> T execute(SessionCallBack<T> sessionCallBack) {
		String terminalId = null;
		try {
			terminalId = startSession();
			return sessionCallBack.doInSession(terminalId);
		} finally {
			if (terminalId != null) {
				endSession(terminalId);
			}
		}
	}

	protected String startSession() {
		Source result = sendAndRecevie(XMLDoc.newDocument(true)
				.addRoot("AK_BSI").addTag("HOST").setText(host).gotoParent()
				.addTag("ID").setText(id).gotoParent().addTag("PASSWORD")
				.setText(password).toSource());

		return XMLDoc.from(result, true).gotoRoot().getText("TERMINAL");
	}

	protected void endSession(String terminal) {
		sendAndRecevie(XMLDoc.newDocument(true).addRoot("AK_BSO")
				.addTag("TERMINAL").setText(terminal).gotoParent()
				.addTag("HOST").setText(host).gotoParent().addTag("ID")
				.setText(id).toSource());
	}

	private Source sendAndRecevie(Source input) {
		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(aikioskServerUrl);

		try {
			StringWriter writer = new StringWriter();
			TransformerFactory.newInstance().newTransformer().transform(input, new StreamResult(writer));
			String request = writer.toString();
			postMethod.setRequestHeader("Authorization", "Basic aW50ZXJnbG9iYWw6bmlnZXJpYQ==");
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

			postMethod.setRequestBody(new NameValuePair[] { new NameValuePair( "XML", request) });


			int status = httpClient.executeMethod(postMethod);

			if (status == HttpStatus.SC_OK) {
				Source response = new StringSource(postMethod.getResponseBodyAsString().trim());
				return response;
			} else {
				throw new RuntimeException("returned unexpected status " + status);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			postMethod.releaseConnection();
		}
	}


}
