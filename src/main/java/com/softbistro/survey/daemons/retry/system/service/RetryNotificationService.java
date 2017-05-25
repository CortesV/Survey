package com.softbistro.survey.daemons.retry.system.service;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;
import com.softbistro.survey.daemons.notification.system.service.FormingMessage;
import com.softbistro.survey.daemons.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.retry.system.component.interfaces.IRetryNotification;

@Service
@Scope("prototype")
public class RetryNotificationService {
	private Session session;
	private Notification message;
	private ISendingMessage iSendingMessage;
	private IRetryNotification iRetryNotification;
	private ScheduledExecutorService scheduledExecutorServiceTimerRetryNotification;

	private int maxCountOfRetryResendMessage = 3;

	private Logger log = LogManager.getLogger(getClass());

	public RetryNotificationService(Session session, Notification message, ISendingMessage iSendingMessage,
			IRetryNotification iRetryNotification,
			ScheduledExecutorService scheduledExecutorServiceTimerRetryNotification) {
		this.session = session;
		this.message = message;
		this.iSendingMessage = iSendingMessage;
		this.iRetryNotification = iRetryNotification;
		this.scheduledExecutorServiceTimerRetryNotification = scheduledExecutorServiceTimerRetryNotification;
	}

	public void retrySend() {

		RetryNotification messageInFailureTable = iRetryNotification.getEmailForResending(message.getId());
		iRetryNotification.updateIncreaseRetryCountForMessageToResend(messageInFailureTable.getId());

		if (Objects.nonNull(messageInFailureTable)) {
			try {
				log.info(String.format(
						"NotSys | RetrySys | Sender email: %s | CC Receiver email: %s | Receiver email: %s | Header: %s",
						messageInFailureTable.getSenderEmail(), messageInFailureTable.getReceiverCCEmail(),
						messageInFailureTable.getReceiverEmail(), messageInFailureTable.getHeader()));

				iRetryNotification.updateStatusRetryMessageToTry(messageInFailureTable.getId());

				log.info(String.format(
						"NotSys | RetrySys | Status of message [%s] was updated on 'TRY' | Number of try: %s.",
						messageInFailureTable.getId(), messageInFailureTable.getRetryCount() + 1));

				new FormingMessage(session, messageInFailureTable).formingRetryMessage();

				iRetryNotification.updateStatusRetryMessageToSent(messageInFailureTable.getId());
				iSendingMessage.updateStatusMessagesFromErrorToProcessed(messageInFailureTable.getId());

				log.info(String.format(
						"NotSys | RetrySys | Status of message [%s] was updated on 'SENT' and 'PROCESSED'. | Message sent | Number of try: %s.",
						messageInFailureTable.getId(), messageInFailureTable.getRetryCount() + 1));

				scheduledExecutorServiceTimerRetryNotification.shutdown();

			} catch (MessagingException e) {
				iRetryNotification.updateStatusRetryMessageToError(messageInFailureTable.getId());

				log.info(String.format(
						"NotSys | RetrySys | Status of message [%s] was updated on 'ERROR' in failure table.",
						messageInFailureTable.getId()));

			} finally {
				if (messageInFailureTable.getRetryCount() == (maxCountOfRetryResendMessage - 1)) {
					iRetryNotification.updateStatusRetryMessageToVerifiedError(messageInFailureTable.getId());

					log.info(
							String.format("NotSys | RetrySys | Status of message [%s] was updated on 'VERIFIED_ERROR'.",
									messageInFailureTable.getId()));

					scheduledExecutorServiceTimerRetryNotification.shutdown();
				}
			}
		}
	}

}
