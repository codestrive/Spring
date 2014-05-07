package com.ikiosksng.airkiosk.hub.common;

public interface MailService {

	boolean sendMail(MailDataModel mailDataModel, String recipient);

}
