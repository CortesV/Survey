package com.softbistro.survey.daemons.notification.system.threads;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.interfaces.ISending;

public class MessageEmailThread implements Runnable, ISending {
	private Session session;
	private List<Notification> messages;
	private int emailIndex;
	private String messageTheme;
	private String messageText;
	private String username;

	public MessageEmailThread(Session session, List<Notification> messages, int emailIndex, String messageTheme,
			String messageText, String username) {
		this.session = session;
		this.messages = messages;
		this.emailIndex = emailIndex;
		this.messageTheme = messageTheme;
		this.messageText = messageText;
		this.username = username;
	}

	/**
	 * Sending message on email about registration<br>
	 * then store information in database
	 */
	@Override
	public void sendMessage() {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(messages.get(emailIndex).getReceiverEmail()));

			message.setSubject(messageTheme);
			message.setText(messageText);

			Transport.send(message);
			System.out.println("Message sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		sendMessage();

	}
}
