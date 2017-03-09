package com.survey.softbistro.notification.system.service;

import java.util.Properties;

/**
 * For connection to email for sending
 * 
 * @author zviproject
 *
 */
public class ConnectionToEmail {

	/**
	 * Data about account that will sending messages
	 */
	protected static final String USERNAME = "softbistrosurvey@gmail.com";
	protected static final String PASSWORD = "20170903";

	protected Properties props;

	public ConnectionToEmail() {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

}
