package com.softbistro.survey.daemons.notification.system.retry.system.threads;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.main.system.interfaces.ISendingMessages;
import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces.IRetryNotification;

@Service
@Scope("prototype")
public class RetryNotificationService implements Runnable, ISendingMessages {
	/**
	 * Data about account that will sending messages
	 */
	private String username;
	private String password;

	private List<RetryNotification> messagesForRetryThread;
	private IRetryNotification iRetryNotification;
	private Properties propertiesSurvey;

	private Logger log = LogManager.getLogger(getClass());

	public RetryNotificationService(List<RetryNotification> messagesForRetryThread,
			IRetryNotification iRetryNotification, Properties propertiesSurvey) {
		this.messagesForRetryThread = messagesForRetryThread;
		this.iRetryNotification = iRetryNotification;
		this.propertiesSurvey = propertiesSurvey;
	}

	public void send() {

		if (messagesForRetryThread.isEmpty()) {
			return;
		}

		log.info(String.format("NotSys | Status the list of messages was updated on 'IN_PROCESS'. Size of list: %s.",
				messagesForRetryThread.size()));

		messagesForRetryThread.forEach(message -> {
			createSessionAndThreadForSendEmail(messagesForRetryThread, messagesForRetryThread.indexOf(message));
		});

	}

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

		log.info(String.format(
				"NotSys | New thread | Sender email: %s | CC Receiver email: %s | Receiver email: %s | ID: %s",
				messagesForRetryThread.get(emailIndex).getSenderEmail(),
				messagesForRetryThread.get(emailIndex).getReceiverCCEmail(),
				messagesForRetryThread.get(emailIndex).getReceiverEmail(),
				messagesForRetryThread.get(emailIndex).getId()));

	}

	@Override
	public void run() {
		send();
	}
}
