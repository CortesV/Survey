package com.survey.softbistro.sending.gmail.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.softbistro.sending.gmail.component.interfacee.IEmail;

/**
 * Sending message to users
 * 
 * @author zviproject
 *
 */

@Service
public class EmailSenderService {
	private static final String USERNAME = "zarovni03@gmail.com";
	private static final String PASSWORD = "19991904";
	private Properties props;

	@Autowired
	IEmail iEmail;

	public EmailSenderService() {
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
	public void send() {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {

			for (String email : iEmail.getEmailsForSending()) {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(USERNAME));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject(generateThemeFoeMessage());
				message.setText(generateTextForMessage());

				Transport.send(message);
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private String generateTextForMessage() {
		String urlForVote = String.format("https://trainee.atlassian.net/%s",
				DigestUtils.md5Hex("projects/SUR/summary"));

		String textMessage = String.format("Survey on theme %s by %s was created.\n", "Testing", "SoftBistro")
				+ String.format("You can vote by clicking on URL: %s", urlForVote);
		return textMessage;
	}

	private String generateThemeFoeMessage() {
		String themeOfMessage = "SoftBistro";
		return String.format("Survey %s", themeOfMessage);
	}

}
