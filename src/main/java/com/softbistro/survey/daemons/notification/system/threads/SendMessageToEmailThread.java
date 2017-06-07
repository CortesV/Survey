package com.softbistro.survey.daemons.notification.system.threads;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfaces.INotification;
import com.softbistro.survey.daemons.notification.system.interfaces.ISending;
import com.softbistro.survey.daemons.notification.system.service.FormMessageForSendService;
import com.softbistro.survey.daemons.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.retry.system.component.interfaces.IRetryNotification;

/**
 * @author vlad
 *
 */
public class SendMessageToEmailThread implements Runnable, ISending {
	private Session session;
	private List<Notification> messagesForThread;
	private int emailIndex;
	private INotification iSendingMessage;
	private IRetryNotification iRetryNotification;

	private Logger log = LogManager.getLogger(getClass());

	public SendMessageToEmailThread(Session session, List<Notification> messagesForThread, int emailIndex,
			INotification iSendingMessage, IRetryNotification iRetryNotification) {
		this.session = session;
		this.messagesForThread = messagesForThread;
		this.emailIndex = emailIndex;
		this.iSendingMessage = iSendingMessage;
		this.iRetryNotification = iRetryNotification;
	}

	/**
	 * Sending message on email about registration<br>
	 * then store information in database
	 */
	@Override
	public void sendMessage() {
		try {

			new FormMessageForSendService(session, messagesForThread.get(emailIndex)).formingMessage();

			iSendingMessage.updateStatusMessagesFromInProcessToProcessed(messagesForThread.get(emailIndex).getId());

			log.info(String.format("NotSys | Status of message [%s] was updated on 'PROCESSED'. | Message sent.",
					messagesForThread.get(emailIndex).getId()));

		} catch (MessagingException e) {

			iSendingMessage.updateStatusMessagesToError(messagesForThread.get(emailIndex).getId());
			addErrorMessageToNotificationFailureTable((e.getMessage()));

			log.info(String.format("NotSys | Status of message [%s] was updated on 'ERROR'. | Add to failure table.",
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
		RetryNotification retryNotification = new RetryNotification(messagesForThread.get(emailIndex).getId(), textException);
		iRetryNotification.insertRetryNotification(retryNotification);
	}

	@Override
	public void run() {
		sendMessage();

	}
}
