package com.softbistro.survey.notification.db.service;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.manage.components.interfaces.IClient;
import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;
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

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	@Autowired
	private IClient iClient;
	/**
	 * Data about account that will sending messages
	 */
	@Value("${password.mail.username}")
	private String username;

	@Value("${password.text.for.sending.url}")
	private String url;

	/**
	 * Sending message to database
	 */
	@Override
	public void send() {
		List<String> emails = iClient.getEmailOfNewPassword();
		emails.stream().forEach(email -> {
			Notification notification = new Notification(username, email, generateThemeForMessage(),
					generateTextForMessage(email));
		
			iSendingMessage.insertIntoNotification(notification);
			log.info(String.format("Password email: %s", email));
		});
	}

	/**
	 * Generate text for message
	 * 
	 * @param email,
	 *            uuid
	 */
	@Override
	public String generateTextForMessage(String mail) {
		String urlForVote = url + UUID.randomUUID().toString();

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
