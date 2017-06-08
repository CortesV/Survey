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

import com.softbistro.survey.client.manage.components.entity.ClientForSending;
import com.softbistro.survey.client.manage.components.interfaces.IClient;
import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.component.interfaces.INotification;
import com.softbistro.survey.notification.db.entity.NotificationClientSending;
import com.softbistro.survey.notification.db.interfacee.ICreateMessage;

/**
 * For creating and sending message that will contain information about new user
 * for confirm registration
 * 
 * @author alex_alokhin, zviproject
 *
 */
@Service
@Scope("prototype")
public class RegistrationMessageServise implements ICreateMessage {
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private INotification iSendingMessage;

	@Autowired
	private IClient iClient;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${client.mail.username}")
	private String username;

	@Value("${client.text.for.sending.url}")
	private String url;

	private Date date = new Date(System.currentTimeMillis());

	@Value("${client.url.duration.days}")
	private Integer durationDays;

	/**
	 * Sending message to database
	 */
	@Override
	public void send() {
		List<ClientForSending> clients = iClient.getNewClients();

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

			NotificationClientSending notificationSending = new NotificationClientSending(uuid, client.getId(),
					new Date(cal.getTimeInMillis()));
			iSendingMessage.insertIntoSendingClient(notificationSending);

			iClient.updateStatusOfNewClients();

			log.info(String.format("Registration email: %s", client.getEmail()));
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
				"Registration new account with email \"%s\" \n" + "For confirm click on URL : %s", email, urlForVote);
		return textMessage;
	}

	/**
	 * Generate theme of message
	 */
	@Override
	public String generateThemeForMessage() {
		return String.format("Registration");
	}

}
