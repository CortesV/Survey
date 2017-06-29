package com.softbistro.survey.daemons.notification.system.retry.system.service;

import java.util.Objects;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.softbistro.survey.daemons.notification.system.main.system.interfaces.IFormingMessage;
import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;


/**
 * Forming message for email that will be sent then
 * 
 * @throws MessagingException
 * @throws AddressException
 */
public class FormMessageForSendServiceRetry implements IFormingMessage {
	private Session session;
	private RetryNotification retryMessage;

	public FormMessageForSendServiceRetry(Session session, RetryNotification retryMessage) {
		this.session = session;
		this.retryMessage = retryMessage;
	}

	/**
	 * Forming message for email that will be sent then (for retry system)
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public void formingMessage() throws AddressException, MessagingException {

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
