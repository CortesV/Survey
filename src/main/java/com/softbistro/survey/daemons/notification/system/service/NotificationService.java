package com.softbistro.survey.daemons.notification.system.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;
import com.softbistro.survey.daemons.notification.system.threads.MessageEmailThread;

@Service
@Scope("prototype")
public class NotificationService implements Runnable {
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	@Autowired
	private Properties propertiesSurvey;

	/**
	 * Data about account that will sending messages
	 */
	private String username;
	private String password;

	public void send() {

		List<Notification> messages = iSendingMessage.getEmailsForSending();

		iSendingMessage.updateStatusMessagesToInProcess();
		log.info(String.format("Status the list of messages was updated on 'IN_PROCESS'. Size of list: %s.",
				messages.size()));

		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {

			username = messages.get(emailIndex).getSenderEmail();
			password = messages.get(emailIndex).getSenderPassword();

			Session session = Session.getInstance(propertiesSurvey, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			new Thread(new MessageEmailThread(session, messages, emailIndex, messages.get(emailIndex).getHeader(),
					messages.get(emailIndex).getBody(), username, iSendingMessage)).start();

			log.info(String.format("Sender email: %s | Receiver email: %s | Header: %s",
					messages.get(emailIndex).getSenderEmail(), messages.get(emailIndex).getReceiverEmail(),
					messages.get(emailIndex).getHeader()));
		}
	}

	@Override
	public void run() {
		send();
	}
}
