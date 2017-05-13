package com.softbistro.survey.notification.db.service;

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

import com.softbistro.survey.client.manage.components.interfaces.IClient;
import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.entity.NotificationClientSending;
import com.softbistro.survey.daemons.notification.system.component.entity.NotificationSurveySending;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;
import com.softbistro.survey.notification.db.interfacee.ICreateMessage;

/**
 * For creating and sending message, that will contain information about survey
 * for participant
 * 
 * @author alex_alokhin, zviproject
 *
 */
@Service
@Scope("prototype")
public class SurveyMessageService implements ICreateMessage {
	private static final Logger log = LogManager.getLogger(SurveyMessageService.class);

	@Autowired
	private ISendingMessage iSendingMessage;

	@Autowired
	private IClient iClient;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${survey.mail.username}")
	private String username;

	@Value("${survey.text.for.sending.url}")
	private String url;

	/**
	 * Sending message to database
	 */
	public void send() {
		List<String> emails = iClient.getEmailsForSendingSurvey();
		String uuid = UUID.randomUUID().toString();
		emails.stream().forEach(email -> {
			Notification notification = new Notification(username, email, generateThemeForMessage(),
					generateTextForMessage(email, uuid));
			iSendingMessage.insertIntoNotification(notification);
			log.info(String.format("Participant email: %s", email));
		});

		List<NotificationSurveySending> ids = iClient.getIdStartedSurvey();
		ids.stream().forEach(id -> {
			NotificationSurveySending notificationSurveySending = new NotificationSurveySending(uuid,
					id.getParticipantId(), id.getSurveyId());
			iSendingMessage.insertIntoSendingSurvey(notificationSurveySending);
		});

		iClient.updateStatusOfSurvey();
	}

	/**
	 * Generate text for message
	 * 
	 * @param email
	 */
	@Override
	public String generateTextForMessage(String email, String uuid) {
		String urlForVote = url + uuid;

		String textMessage = String.format(
				"Participant with mail \"%s\" started survey\nYou can vote by clicking on URL : %s", email, urlForVote);
		return textMessage;
	}

	/**
	 * Generate theme of message
	 */
	@Override
	public String generateThemeForMessage() {
		return String.format("Survey");
	}

}
