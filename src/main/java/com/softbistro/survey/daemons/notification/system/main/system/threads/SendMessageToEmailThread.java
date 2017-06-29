package com.softbistro.survey.daemons.notification.system.main.system.threads;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.component.interfaces.INotification;
import com.softbistro.survey.daemons.notification.system.main.system.interfaces.IFormingMessage;
import com.softbistro.survey.daemons.notification.system.main.system.interfaces.ISendingMessage;
import com.softbistro.survey.daemons.notification.system.main.system.service.FormMessageForSendService;
import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces.IRetryNotification;

/**
 * For work with messages: - sending message;
 * 
 * @author vlad
 */
@Service
@Scope("prototype")
public class SendMessageToEmailThread implements Runnable, ISendingMessage {
	private Session session;
	private List<Notification> messagesForThread;
	private int emailIndex;
	private INotification iSendingMessage;
	private IRetryNotification iRetryNotification;

	private static final Logger LOGGER = Logger.getLogger(SendMessageToEmailThread.class);

	public SendMessageToEmailThread(Session session, List<Notification> messagesForThread, int emailIndex,
			INotification iSendingMessage, IRetryNotification iRetryNotification) {
		this.session = session;
		this.messagesForThread = messagesForThread;
		this.emailIndex = emailIndex;
		this.iSendingMessage = iSendingMessage;
		this.iRetryNotification = iRetryNotification;
	}

	/**
	 * For sending message in separate thread. Try sending message on email for
	 * everyone message in separate thread.
	 */
	@Override
	public void sendMessage() {
		try {

			IFormingMessage iFormingMessage = new FormMessageForSendService(session, messagesForThread.get(emailIndex));

			iFormingMessage.formingMessage();

			iSendingMessage.updateStatusMessagesFromInProcessToProcessed(messagesForThread.get(emailIndex).getId());

			LOGGER.info(String.format("NotSys | Status of message [%s] was updated on 'PROCESSED'. | Message sent.",
					messagesForThread.get(emailIndex).getId()));

		} catch (MessagingException e) {

			iSendingMessage.updateStatusMessagesToError(messagesForThread.get(emailIndex).getId());
			addErrorMessageToNotificationFailureTable((e.getMessage()));

			LOGGER.info(String.format("NotSys | Status of message [%s] was updated on 'ERROR'. | Add to failure table.",
					messagesForThread.get(emailIndex).getId()));
		}
	}

	/**
	 * Add to info about message that has some exception into database
	 * "notification" in table "notification_failure"
	 * 
	 * @param (textException),
	 *            text from exception
	 */
	private void addErrorMessageToNotificationFailureTable(String textException) {
		RetryNotification retryNotification = new RetryNotification(messagesForThread.get(emailIndex).getId(),
				textException);
		iRetryNotification.insertRetryNotification(retryNotification);
	}

	@Override
	public void run() {
		sendMessage();

	}
}
