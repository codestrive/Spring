package com.ikiosksng.airkiosk.hub.common;

import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.xml.transform.StringSource;

import com.ikiosksng.airkiosk.hub.aerocrs.services.Destination;

public class SendReceive {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Destination.class);
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy/MM/dd");

	@Value("${aerocrs.password}")
	private String password;

	@Value("${aerocrs.token}")
	private String id;

	@Value("${aerocrs.api.url}")
	private String aeroCSRAPIUrl;

	

	public Source sendAndRecevie(Source input) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("https://connect.aerocrs.com/v3/");
		System.out.println(aeroCSRAPIUrl);
		try {
			StringWriter writer = new StringWriter();
			TransformerFactory.newInstance().newTransformer()
					.transform(input, new StreamResult(writer));
			String request = writer.toString();
			postMethod.setRequestHeader("Content-Type",
					"text/xml");
			postMethod.setRequestBody(request.toString());
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("sending request");
				LOGGER.debug(request);
			}

			int status = httpClient.executeMethod(postMethod);

			LOGGER.info("returned status " + status);

			if (status == HttpStatus.SC_OK) {
				System.out.println("response ok");
				Source response = new StringSource(postMethod
						.getResponseBodyAsString().trim());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(response.toString());
				}
				return response;
			} else {
				throw new RuntimeException("returned unexpected status "
						+ status);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
