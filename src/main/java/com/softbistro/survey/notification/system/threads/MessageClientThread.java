package com.softbistro.survey.notification.system.threads;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;

/**
 * Start thread for sending message about client
 * 
 * @author zviproject
 *
 */
public class MessageClientThread implements Runnable {

	private Session session;
	private List<RegistrationMessage> messages;
	private int emailIndex;
	private String messageTheme;
	private String messageText;
	private String username;

	public MessageClientThread(Session session, List<RegistrationMessage> messages, int emailIndex, String messageTheme,
			String messageText, String username) {
		this.session = session;
		this.messages = messages;
		this.emailIndex = emailIndex;
		this.messageTheme = messageTheme;
		this.messageText = messageText;
		this.username = username;
	}

	/**
	 * Sending message on email
	 */
	public void sendMessage() {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(messages.get(emailIndex).getClientEmail()));

			message.setSubject(messageTheme);
			message.setText(messageText);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		sendMessage();

	}

}
