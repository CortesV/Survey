package com.softbistro.survey.daemons.retry.system.threads;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.softbistro.survey.daemons.notification.system.interfaces.ISending;
import com.softbistro.survey.daemons.notification.system.service.FormMessageForSendService;
import com.softbistro.survey.daemons.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.retry.system.component.interfaces.IRetryNotification;

public class RetrySendMessageToEmailThread implements Runnable, ISending {
	private Session session;
	private List<RetryNotification> messagesForRetryThread;
	private int emailIndex;
	private IRetryNotification iRetryNotification;

	private Logger log = LogManager.getLogger(getClass());

	public RetrySendMessageToEmailThread(Session session, List<RetryNotification> messagesForRetryThread,
			int emailIndex, IRetryNotification iRetryNotification) {
		this.session = session;
		this.messagesForRetryThread = messagesForRetryThread;
		this.emailIndex = emailIndex;
		this.iRetryNotification = iRetryNotification;
	}

	/**
	 * Sending message on email about registration<br>
	 * then store information in database
	 */
	@Override
	public void sendMessage() {
		try {

			new FormMessageForSendService(session, messagesForRetryThread.get(emailIndex)).formingRetryMessage();

			iRetryNotification.updateStatusMessagesFromErrorToProcessed(messagesForRetryThread.get(emailIndex).getId());

			log.info(String.format("NotSys | Status of message [%s] was updated on 'PROCESSED'. | Message sent.",
					messagesForRetryThread.get(emailIndex).getId()));

		} catch (MessagingException e) {

			iRetryNotification.updateStatusMessagesToError(messagesForRetryThread.get(emailIndex).getId());

			log.info(String.format("NotSys | Status of message [%s] was updated on 'ERROR'. | Number of try: [%s].",
					messagesForRetryThread.get(emailIndex).getId(),
					messagesForRetryThread.get(emailIndex).getRetryCount()));
		}
	}

	@Override
	public void run() {
		sendMessage();

	}
}
