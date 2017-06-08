package com.softbistro.survey.daemons.notification.system.main.system.service;

import java.util.Objects;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.interfaces.IFormingMessage;


/**
 * Forming message for email that will be sent then
 * 
 * @throws MessagingException
 * @throws AddressException
 */
public class FormMessageForSendService implements IFormingMessage {
	private Session session;
	private Notification message;

	/**
	 * Use in class (SendMessageToEmailThread) for forming message.
	 * 
	 * @param session
	 * @param message
	 */
	public FormMessageForSendService(Session session, Notification message) {
		this.session = session;
		this.message = message;
	}

	/**
	 * Forming message for email that will be sent then
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

}
