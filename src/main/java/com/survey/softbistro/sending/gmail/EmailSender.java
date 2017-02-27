package com.survey.softbistro.sending.gmail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Sending message to users
 * 
 * @author zviproject
 *
 */
public class EmailSender {
	private static final String USERNAME = "zarovni03@gmail.com";
	private static final String PASSWORD = "19991904";
	private Properties props;

	public EmailSender() {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	/**
	 * Sending message from main accaunt to email of users
	 * 
	 * @param toEmail
	 *            - receiver message
	 */
	public void send(String toEmail) {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(generateThemeFoeMessage());
			message.setText(generateTextForMessage());

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String generateTextForMessage() {
		String textMessage = "HELLO)";
		return textMessage;
	}

	public String generateThemeFoeMessage() {
		String themeOfMessage = "SoftBistro";
		return String.format("Survey %s", themeOfMessage);
	}

}
