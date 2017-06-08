package com.softbistro.survey.daemons.notification.system.main.system.threads;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.component.interfaces.INotification;
import com.softbistro.survey.daemons.notification.system.main.system.interfaces.ICreateThreadForMessage;
import com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces.IRetryNotification;

/**
 * Service which start working with messages for sending. For work with
 * messages: - create threads for everyone message;
 * 
 * @author vlad
 *
 */
@Service
@Scope("prototype")
public class NotificationService implements Runnable, ICreateThreadForMessage {

	private static final Logger LOGGER = Logger.getLogger(NotificationService.class);

	/**
	 * Data about account that will sending messages
	 */
	private String username;
	private String password;

	private List<Notification> messagesForThread;
	private INotification iSendingMessage;
	private IRetryNotification iRetryNotification;
	private Properties propertiesSurvey;

	public NotificationService(List<Notification> messagesForThread, INotification iSendingMessage,
			IRetryNotification iRetryNotification, Properties propertiesSurvey) {
		this.messagesForThread = messagesForThread;
		this.iSendingMessage = iSendingMessage;
		this.iRetryNotification = iRetryNotification;
		this.propertiesSurvey = propertiesSurvey;
	}

	/**
	 * Creating thread for everyone message. Start to sending messages in
	 * separate thread.
	 */
	public void createThreadForMessage() {

		if (messagesForThread.isEmpty()) {
			return;
		}

		LOGGER.info(String.format("NotSys | Status the list of messages was updated on 'IN_PROCESS'. Size of list: %s.",
				messagesForThread.size()));

		messagesForThread.forEach(message -> {
			createSessionAndThreadForSendEmail(messagesForThread, messagesForThread.indexOf(message));
		});

	}

	/**
	 * Create message with session (sender email, sender password) and other
	 * information which need to start sending information.
	 * 
	 * @param messagesForThread
	 * @param emailIndex
	 */
	private void createSessionAndThreadForSendEmail(List<Notification> messagesForThread, int emailIndex) {
		username = messagesForThread.get(emailIndex).getSenderEmail();
		password = messagesForThread.get(emailIndex).getSenderPassword();

		Session session = Session.getInstance(propertiesSurvey, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		new Thread(new SendMessageToEmailThread(session, messagesForThread, emailIndex, iSendingMessage,
				iRetryNotification)).start();

		LOGGER.info(String.format(
				"NotSys | New thread | Sender email: %s | CC Receiver email: %s | Receiver email: %s | ID: %s",
				messagesForThread.get(emailIndex).getSenderEmail(),
				messagesForThread.get(emailIndex).getReceiverCCEmail(),
				messagesForThread.get(emailIndex).getReceiverEmail(), messagesForThread.get(emailIndex).getId()));

	}

	@Override
	public void run() {
		createThreadForMessage();
	}
}
