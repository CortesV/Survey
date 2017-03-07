package com.survey.softbistro.notification.system.service;

import java.util.List;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.survey.softbistro.notification.system.component.entity.RegistrationMessage;

@Service
public class RegistrationMessageServise extends SurveyMessageService {
	private Logger log = LogManager.getLogger(getClass());

	@Override
	public void send(int page) {
		try {
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});

			List<RegistrationMessage> messages = iSurveyMessage.getEmailOfNewClients(page);

			for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(USERNAME));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(messages.get(emailIndex).getClientEmail()));
				message.setSubject(generateThemeForMessage());
				message.setText(generateTextForMessage(messages.get(emailIndex)));

				Transport.send(message);

				log.info(String.format("Register email: %s", messages.get(emailIndex).getClientEmail()));
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private String generateTextForMessage(RegistrationMessage client) {
		String urlForVote = String.format("http://localhost:8080/survey/client_name%s", client.getClientName());

		String textMessage = String.format(
				"Registration new account with name \"%s\" \n" + "For confirm click on URL :%s", client.getClientName(),
				urlForVote);
		return textMessage;
	}

}
