package com.softbistro.survey.daemons.notification.system.retry.system.threads;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.main.system.interfaces.ICreateThreadForMessage;
import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;
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
public class RetryNotificationService implements Runnable, ICreateThreadForMessage {

	private static final Logger LOGGER = Logger.getLogger(RetryNotificationService.class);

	/**
	 * Data about account that will sending messages
	 */
	private String username;
	private String password;

	private List<RetryNotification> messagesForRetryThread;
	private IRetryNotification iRetryNotification;
	private Properties propertiesSurvey;

	public RetryNotificationService(List<RetryNotification> messagesForRetryThread,
			IRetryNotification iRetryNotification, Properties propertiesSurvey) {
		this.messagesForRetryThread = messagesForRetryThread;
		this.iRetryNotification = iRetryNotification;
		this.propertiesSurvey = propertiesSurvey;
	}

	/**
	 * Creating thread for everyone message. Start to sending messages in
	 * separate thread.
	 */
	public void createThreadForMessage() {

		if (messagesForRetryThread.isEmpty()) {
			return;
		}

		LOGGER.info(String.format("NotSys | Status the list of messages was updated on 'IN_PROCESS'. Size of list: %s.",
				messagesForRetryThread.size()));

		messagesForRetryThread.forEach(message -> {
			createSessionAndThreadForSendEmail(messagesForRetryThread, messagesForRetryThread.indexOf(message));
		});

	}

	/**
	 * Create message with session (sender email, sender password) and other
	 * information which need to start sending information.
	 * 
	 * @param messagesForRetryThread
	 * @param emailIndex
	 */
	private void createSessionAndThreadForSendEmail(List<RetryNotification> messagesForRetryThread, int emailIndex) {
		username = messagesForRetryThread.get(emailIndex).getSenderEmail();
		password = messagesForRetryThread.get(emailIndex).getSenderPassword();

		Session session = Session.getInstance(propertiesSurvey, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		new Thread(new RetrySendMessageToEmailThread(session, messagesForRetryThread, emailIndex, iRetryNotification))
				.start();

		LOGGER.info(String.format(
				"NotSys | New thread | Sender email: %s | CC Receiver email: %s | Receiver email: %s | ID: %s",
				messagesForRetryThread.get(emailIndex).getSenderEmail(),
				messagesForRetryThread.get(emailIndex).getReceiverCCEmail(),
				messagesForRetryThread.get(emailIndex).getReceiverEmail(),
				messagesForRetryThread.get(emailIndex).getId()));

	}

	@Override
	public void run() {
		createThreadForMessage();
	}
}
