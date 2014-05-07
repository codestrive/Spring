package com.ikiosksng.airkiosk.hub.freemarker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ikioskng.airkiosks.ws.types.BookOnHoldRequest;
import com.ikiosksng.airkiosk.hub.common.MailDataModel;
import com.ikiosksng.airkiosk.hub.common.MailService;
import com.ikiosksng.airkiosk.hub.common.TemplateService;
import com.ikiosksng.airkiosk.hub.ws.BookingService;

@ContextConfiguration("classpath:META-INF/spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FTLTemplateTests {

	@Autowired
	TemplateService templateService;
	@Autowired
	MailService mailService;
	
	@Autowired
	BookingService bookingService;

	MockServletContext mockServletContext = new MockServletContext(	"file:src/main/webapp");

	@Test
	public void contextTest() {
		templateService.setMockContext(mockServletContext);
		templateService.setTemplate("helloworld.ftl");
		BookOnHoldRequest bohRequest = new BookOnHoldRequest();
//		bohRequest.setPnr("ZTAVRM");
//		bohRequest.setAtmReference("2345232123");
//		bohRequest.setBohCharge("1000");
		bookingService.bookFlightOnHold(bohRequest);
	}
}
