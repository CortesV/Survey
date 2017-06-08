package com.softbistro.survey.notification.db.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.manage.components.interfaces.IClient;
import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.component.interfaces.INotification;
import com.softbistro.survey.notification.db.entity.NotificationSurveySending;
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
	private static final Logger LOGGER = LogManager.getLogger(SurveyMessageService.class);

	@Autowired
	private INotification iSendingMessage;

	@Autowired
	private IClient iClient;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${survey.mail.username}")
	private String username;

	@Value("${survey.text.for.sending.url}")
	private String url;

	private Date date = new Date(System.currentTimeMillis());

	@Value("${client.url.duration.days}")
	private Integer durationDays;

	/**
	 * Sending message to database
	 */
	public void send() {
		List<NotificationSurveySending> clients = iClient.getClientsForSendingSurvey();

		clients.stream().forEach(client -> {
			String uuid = UUID.randomUUID().toString();

			Notification notification = new Notification(username, client.getEmail(), generateThemeForMessage(),
					generateTextForMessage(client.getEmail(), uuid));
			iSendingMessage.insertIntoNotification(notification);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, durationDays);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			NotificationSurveySending notificationSending = new NotificationSurveySending(uuid,
					client.getParticipantId(), client.getSurveyId(), new Date(cal.getTimeInMillis()));
			iSendingMessage.insertIntoSendingSurvey(notificationSending);

			iClient.updateStatusOfSurvey();

			LOGGER.info(String.format("Client email that started survey : %s", client.getEmail()));
		});
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
