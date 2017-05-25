package com.softbistro.survey.daemons.notification.system.service;

import java.util.Objects;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.retry.system.component.entity.RetryNotification;

public class FormingMessage {
	private Session session;
	private RetryNotification retryMessage;
	private Notification message;

	public FormingMessage(Session session, RetryNotification retryMessage) {
		this.session = session;
		this.retryMessage = retryMessage;
	}

	public FormingMessage(Session session, Notification message) {
		this.session = session;
		this.message = message;
	}

	/**
	 * Forming message on email about registration<br>
	 * then store information in database for message
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public void formingMessage() throws AddressException, MessagingException {

		Message messageForSend = new MimeMessage(session);
		messageForSend.setFrom(new InternetAddress(message.getSenderEmail()));

		if (Objects.nonNull(message.getReceiverCCEmail())) {
			messageForSend.setRecipients(Message.RecipientType.CC, InternetAddress.parse(message.getReceiverCCEmail()));
		}

		messageForSend.setRecipients(Message.RecipientType.TO, InternetAddress.parse(message.getReceiverEmail()));

		messageForSend.setSubject(message.getHeader());
		messageForSend.setText(message.getBody());

		Transport.send(messageForSend);

	}

	/**
	 * Forming message on email about registration<br>
	 * then store information in database for retry message
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public void formingRetryMessage() throws AddressException, MessagingException {

		Message messageForSend = new MimeMessage(session);
		messageForSend.setFrom(new InternetAddress(retryMessage.getSenderEmail()));

		if (Objects.nonNull(retryMessage.getReceiverCCEmail())) {
			messageForSend.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(retryMessage.getReceiverCCEmail()));
		}

		messageForSend.setRecipients(Message.RecipientType.TO, InternetAddress.parse(retryMessage.getReceiverEmail()));

		messageForSend.setSubject(retryMessage.getHeader());
		messageForSend.setText(retryMessage.getBody());

		Transport.send(messageForSend);

	}
}
