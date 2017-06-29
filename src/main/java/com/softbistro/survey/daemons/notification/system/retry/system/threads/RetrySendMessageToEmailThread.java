package com.softbistro.survey.daemons.notification.system.retry.system.threads;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.main.system.interfaces.IFormingMessage;
import com.softbistro.survey.daemons.notification.system.main.system.interfaces.ISendingMessage;
import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces.IRetryNotification;
import com.softbistro.survey.daemons.notification.system.retry.system.service.FormMessageForSendServiceRetry;

/**
 * For work with messages: - sending message;
 * 
 * @author vlad
 */
@Service
@Scope("prototype")
public class RetrySendMessageToEmailThread implements Runnable, ISendingMessage {

	private static final Logger LOGGER = Logger.getLogger(RetrySendMessageToEmailThread.class);

	private Session session;
	private List<RetryNotification> messagesForRetryThread;
	private int emailIndex;
	private IRetryNotification iRetryNotification;

	public RetrySendMessageToEmailThread(Session session, List<RetryNotification> messagesForRetryThread,
			int emailIndex, IRetryNotification iRetryNotification) {
		this.session = session;
		this.messagesForRetryThread = messagesForRetryThread;
		this.emailIndex = emailIndex;
		this.iRetryNotification = iRetryNotification;
	}

	/**
	 * For sending message in separate thread. Try sending message on email for
	 * everyone message in separate thread.
	 */
	@Override
	public void sendMessage() {
		try {

			IFormingMessage iFormingMessage = new FormMessageForSendServiceRetry(session,
					messagesForRetryThread.get(emailIndex));

			iFormingMessage.formingMessage();

			iRetryNotification
					.updateStatusMessagesFromInProcessToProcessed(messagesForRetryThread.get(emailIndex).getId());

			LOGGER.info(String.format("NotSys | Status of message [%s] was updated on 'PROCESSED'. | Message sent.",
					messagesForRetryThread.get(emailIndex).getId()));

		} catch (MessagingException e) {

			iRetryNotification.updateStatusMessagesToError(messagesForRetryThread.get(emailIndex).getId());
			updateTextErrorMessageInNotificationFailureTable((e.getMessage()));

			LOGGER.info(String.format("NotSys | Status of message [%s] was updated on 'ERROR'. | Number of try: [%s].",
					messagesForRetryThread.get(emailIndex).getId(),
					messagesForRetryThread.get(emailIndex).getRetryCount()));
		}
	}

	/**
	 * If field (notification_id) duplicated, then updated field 'exception'
	 * which contain text of exception "notification" in table
	 * "notification_failure"
	 * 
	 * @param (textException),
	 *            text from exception
	 */
	private void updateTextErrorMessageInNotificationFailureTable(String textException) {
		RetryNotification retryNotification = new RetryNotification(messagesForRetryThread.get(emailIndex).getId(),
				textException);
		iRetryNotification.insertRetryNotification(retryNotification);
	}

	@Override
	public void run() {
		sendMessage();

	}
}
