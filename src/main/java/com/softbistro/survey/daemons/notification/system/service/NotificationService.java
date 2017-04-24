package com.softbistro.survey.daemons.notification.system.service;

import java.util.List;
import java.util.Properties;

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
import com.softbistro.survey.daemons.notification.system.threads.MessageEmailThread;

@Service
@Scope("prototype")
public class NotificationService implements Runnable {
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${client.mail.username}")
	protected String username;

	@Value("${client.mail.password}")
	protected String password;

	@Autowired
	private Properties propertiesClient;

	public void send() {
		Session session = Session.getInstance(propertiesClient, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		List<Notification> messages = iSendingMessage.getEmailsForSending();

		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {
			Thread thread = new Thread(new MessageEmailThread(session, messages, emailIndex,
					messages.get(emailIndex).getHeader(), messages.get(emailIndex).getBody(), username));
			thread.start();
			log.info(String.format("Receiver email: %s", messages.get(emailIndex).getReceiverEmail()));
		}

	}

	@Override
	public void run() {
		send();
	}
}
