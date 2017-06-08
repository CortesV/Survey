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
 * For creating and sending message that will contain information about changed
 * password
 * 
 * @author alex_alokhin, zvproject
 *
 */
@Service
@Scope("prototype")
public class ChangePasswordMessageService implements ICreateMessage {

	private Logger LOGGER = LogManager.getLogger(getClass());

	@Autowired
	private INotification iSendingMessage;

	@Autowired
	private IClient iClient;
	/**
	 * Data about account that will sending messages
	 */
	@Value("${password.mail.username}")
	private String username;

	@Value("${password.text.for.sending.url}")
	private String url;

	private Date date = new Date(System.currentTimeMillis());

	@Value("${password.url.duration.days}")
	private Integer durationDays;

	/**
	 * Sending message to database
	 */
	@Override
	public void send() {
		List<ClientForSending> clients = iClient.getClientUpdatePassword();

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

			iSendingMessage.insertIntoSendingPassword(notificationSending);
			
			iClient.updateStatusOfUpdatePassword();

			LOGGER.info(String.format("Changed client password with email: %s", client.getEmail()));
		});
	}

	/**
	 * Generate text for message
	 * 
	 * @param email
	 * @param uuid
	 */
	@Override
	public String generateTextForMessage(String mail, String uuid) {
		String urlForVote = url + uuid;

		String textMessage = String.format(
				"Changed password on account with email \"%s\" \n" + "For confirm click on URL : %s", mail, urlForVote);
		return textMessage;
	}

	/**
	 * Generate theme of message
	 */
	@Override
	public String generateThemeForMessage() {
		return String.format("Changed password");
	}
}
