package com.softbistro.survey.daemons.notification.system.service;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;
import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;
import com.softbistro.survey.notification.system.component.entity.SurveyMessage;
import com.softbistro.survey.notification.system.interfacee.ICreateMessage;

/**
 * For creating and sending message, that will contain information about survey
 * for participant
 * 
 * @author alex_alokhin, zviproject
 *
 */
@Service
@Scope("prototype")
public class SurveyMessageService implements Runnable, ICreateMessage<SurveyMessage> {
	private static final Logger log = LogManager.getLogger(SurveyMessageService.class);

	@Autowired
	private ISendingMessage iSendingMessage;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${survey.mail.username}")
	protected String username;

	@Value("${survey.text.for.sending.url}")
	protected String url;

	/**
	 * Sending message from main account to email of users
	 * 
	 * @param toEmail
	 *            - receiver message
	 */
	public void send() {
		List<String> emails = iSendingMessage.getEmailsForSendingSurvey();

		for (int emailIndex = 0; emailIndex < emails.size(); emailIndex++) {
			String uuid = UUID.randomUUID().toString();
			Notification notification = new Notification();
			notification.setSenderEmail(username);
			notification.setBody(generateTextForMessage(emails.get(emailIndex), uuid));
			notification.setHeader(generateThemeForMessage());
			notification.setReceiverEmail(emails.get(emailIndex));
			
			iSendingMessage.insertIntoNotification(notification);
			log.info(String.format("Password email: %s", emails.get(emailIndex)));

		}
	}

	@Override
	public String generateTextForMessage(String email, String uuid) {
		String urlForVote = url + uuid;

		String textMessage = String.format(
				"Participant with mail \"%s\" started survey\nYou can vote by clicking on URL : %s",
				email, urlForVote);
		return textMessage;
	}

	@Override
	public String generateThemeForMessage() {
		return String.format("Survey");
	}

	@Override
	public void run() {
		send();
	}

}
