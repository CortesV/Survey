package com.softbistro.survey.daemons.notification.system.threads;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;
import com.softbistro.survey.daemons.notification.system.interfaces.ISending;
import com.softbistro.survey.daemons.notification.system.service.FormingMessage;
import com.softbistro.survey.daemons.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.retry.system.component.interfaces.IRetryNotification;
import com.softbistro.survey.daemons.retry.system.service.RetryNotificationService;

/**
 * @author vlad
 *
 */
public class MessageEmailThread implements Runnable, ISending {
	private Session session;
	private List<Notification> messages;
	private int emailIndex;
	private ISendingMessage iSendingMessage;

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private IRetryNotification iRetryNotification;

	public MessageEmailThread(Session session, List<Notification> messages, int emailIndex,
			ISendingMessage iSendingMessage, IRetryNotification iRetryNotification) {
		this.session = session;
		this.messages = messages;
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

			new FormingMessage(session, messages.get(emailIndex)).formingMessage();

			iSendingMessage.updateStatusMessagesFromInProcessToProcessed(messages.get(emailIndex).getId());

			log.info(String.format("NotSys | Status of message [%s] was updated on 'PROCESSED'. | Message sent.",
					messages.get(emailIndex).getId()));

		} catch (MessagingException e) {

			iSendingMessage.updateStatusMessagesToError(messages.get(emailIndex).getId());
			addErrorMessageToNotificationFailureTable((e.getMessage()));

			log.info(String.format("NotSys | Status of message [%s] was updated on 'ERROR'. | Add to failure table.",
					messages.get(emailIndex).getId()));

			retryNotificationTimerStart();
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
		RetryNotification retryNotification = new RetryNotification(messages.get(emailIndex).getId(), textException);
		iRetryNotification.insertRetryNotification(retryNotification);
	}

	/**
	 * After 5 minutes (300 seconds) try to resend message (update status of
	 * message from 'ERROR' to 'NEW' every 30 seconds (number of retries - 3)
	 */
	private void retryNotificationTimerStart() {
		final ScheduledExecutorService scheduledExecutorServiceTimerRetryNotification = Executors
				.newSingleThreadScheduledExecutor();
		scheduledExecutorServiceTimerRetryNotification.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				new RetryNotificationService(session, messages.get(emailIndex), iSendingMessage, iRetryNotification,
						scheduledExecutorServiceTimerRetryNotification).retrySend();
			}
		}, 30, 5, TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		sendMessage();

	}
}
