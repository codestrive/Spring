package com.ikiosksng.airkiosk.hub.common;

import java.io.IOException;
import java.util.Properties;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service("mailService")
public class MailServiceImpl implements MailService {
	
	private static final Logger LOGGER = LoggerFactory .getLogger(MailServiceImpl.class);
	private static final String TEMPLATE_NAME = "bohconfirmationmail.ftl";

	@Autowired
	TemplateService templateService;
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.smtp.auth}")
	private String smtpAuth;
	@Value("${mail.smtp.starttls.enable}")
	private String starttls;
	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.port}")
	private String port;

	Properties props;
	private Session session;

	@PostConstruct
	public void createSession(){
		templateService.setTemplate(TEMPLATE_NAME);
		props = new Properties();
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.starttls.enable", starttls);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
	}

	@Override
	public boolean sendMail(MailDataModel mailDataModel,String recipient) {
		session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
		});
		try{
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient));
			message.setContent(templateService.getOutputAsString(mailDataModel),"text/html" );
			message.setSubject("Booking On Hold Confirmation -"+mailDataModel.getAtmReference());
			LOGGER.info("Sending mails...content:"+message.getContent().toString());
			Transport.send(message);
			LOGGER.info("Sending mails...");
			return true;
		}catch(MessagingException e){
			e.printStackTrace();
			return false;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}

}
