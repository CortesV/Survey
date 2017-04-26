package com.softbistro.survey.notification.db.service;

import java.util.ArrayList;
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
import com.softbistro.survey.notification.db.interfacee.ICreateMessage;

/**
 * For creating and sending message that will contain information about new
 * user for confirm registration
 * 
 * @author alex_alokhin, zviproject
 *
 */
@Service
@Scope("prototype")
public class RegistrationMessageServise implements Runnable, ICreateMessage<Notification> {
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${client.mail.username}")
	protected String username;

	@Value("${client.text.for.sending.url}")
	String url;

	@Override
	public void send() {
		ArrayList<String> emails = iSendingMessage.getEmailOfNewClients();

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
				"Registration new account with email \"%s\" \n" + "For confirm click on URL : %s",
				email, urlForVote);
		return textMessage;
	}

	@Override
	public String generateThemeForMessage() {
		return String.format("Registration");
	}

	@Override
	public void run() {
		send();
	}

}
