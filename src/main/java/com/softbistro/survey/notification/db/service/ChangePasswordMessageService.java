package com.softbistro.survey.notification.db.service;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
public class ChangePasswordMessageService implements Runnable, ICreateMessage {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${password.mail.username}")
	protected String username;

	@Value("${password.text.for.sending.url}")
	String url;
	
	/**
	 * Sending message to database
	 */
	@Override
	public void send() {
		ArrayList<String> emails = iSendingMessage.getEmailOfNewPassword();
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
	
	/**
	 * Generate text for message
	 * 
	 * @param email, uuid
	 */
	@Override
	public String generateTextForMessage(String mail, String uuid) {
		String urlForVote = url + uuid;

		String textMessage = String.format(
				"Changed password on account with email \"%s\" \n" + "For confirm click on URL : %s",
				mail, urlForVote);
		return textMessage;
	}
	
	/**
	 * Generate theme of message
	 */
	@Override
	public String generateThemeForMessage() {
		return String.format("Changed password");
	}

	@Override
	public void run() {
		send();
	}

}
