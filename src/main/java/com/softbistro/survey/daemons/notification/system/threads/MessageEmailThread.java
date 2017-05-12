package com.softbistro.survey.daemons.notification.system.threads;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.interfaces.ISendingMessage;
import com.softbistro.survey.daemons.notification.system.interfaces.ISending;

public class MessageEmailThread implements Runnable, ISending {
	private Session session;
	private List<Notification> messages;
	private int emailIndex;
	private String messageTheme;
	private String messageText;
	private String username;
	private ISendingMessage iSendingMessage;

	public MessageEmailThread(Session session, List<Notification> messages, int emailIndex, String messageTheme,
			String messageText, String username, ISendingMessage iSendingMessage) {
		this.session = session;
		this.messages = messages;
		this.emailIndex = emailIndex;
		this.messageTheme = messageTheme;
		this.messageText = messageText;
		this.username = username;
		this.iSendingMessage = iSendingMessage;
	}

	private Logger log = LogManager.getLogger(getClass());

	/**
	 * Sending message on email about registration<br>
	 * then store information in database
	 */
	@Override
	public void sendMessage() {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			String ReceiverCCEmail = messages.get(emailIndex).getReceiverCCEmail();

			if (ReceiverCCEmail != null && ReceiverCCEmail.length() != 0) {
				message.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(messages.get(emailIndex).getReceiverCCEmail()));
			}

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(messages.get(emailIndex).getReceiverEmail()));

			message.setSubject(messageTheme);
			message.setText(messageText);

			Transport.send(message);

			iSendingMessage.updateStatusMessagesToProcessed();
			log.info("NotSys | Status of message was updated on 'PROCESSED'. | Message sent.");

		} catch (MessagingException e) {

			iSendingMessage.updateStatusMessagesToError();
			log.info("NotSys | Status of message was updated on 'ERROR'.");

			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		sendMessage();

	}
}
