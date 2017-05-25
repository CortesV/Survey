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
import com.softbistro.survey.daemons.retry.system.component.interfaces.IRetryNotification;

@Service
@Scope("prototype")
public class NotificationService implements Runnable {
	/**
	 * Data about account that will sending messages
	 */
	private String username;
	private String password;

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	@Autowired
	private IRetryNotification iRetryNotification;

	@Autowired
	private Properties propertiesSurvey;

	public void send() {

		List<Notification> messages = iSendingMessage.getEmailsForSending();

		iSendingMessage.updateStatusMessagesToInProcess();

		if (!messages.isEmpty()) {
			log.info(
					String.format("NotSys | Status the list of messages was updated on 'IN_PROCESS'. Size of list: %s.",
							messages.size()));

			messages.forEach(message -> {
				createSessionAndThreadForSendEmail(messages, messages.indexOf(message));
			});
		}

	}

	private void createSessionAndThreadForSendEmail(List<Notification> messages, int emailIndex) {
		username = messages.get(emailIndex).getSenderEmail();
		password = messages.get(emailIndex).getSenderPassword();

		Session session = Session.getInstance(propertiesSurvey, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		new Thread(new MessageEmailThread(session, messages, emailIndex, iSendingMessage, iRetryNotification)).start();

		log.info(String.format(
				"NotSys | New thread | Sender email: %s | CC Receiver email: %s | Receiver email: %s | Header: %s",
				messages.get(emailIndex).getSenderEmail(), messages.get(emailIndex).getReceiverCCEmail(),
				messages.get(emailIndex).getReceiverEmail(), messages.get(emailIndex).getHeader()));

	}

	@Override
	public void run() {
		send();
	}
}
