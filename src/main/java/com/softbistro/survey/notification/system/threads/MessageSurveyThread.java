package com.softbistro.survey.notification.system.threads;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

<<<<<<< HEAD:src/main/java/com/survey/softbistro/notification/system/threads/MessageSurveyThread.java
import com.survey.softbistro.notification.system.component.entity.SurveyMessage;
import com.survey.softbistro.notification.system.component.interfacee.ISendingMessage;
import com.survey.softbistro.notification.system.interfacee.ISending;
=======
import com.softbistro.survey.notification.system.component.entity.SurveyMessage;
>>>>>>> 61b3f919c64e2ad59f500faeec5fa96576ba436f:src/main/java/com/softbistro/survey/notification/system/threads/MessageSurveyThread.java

/**
 * Start thread for sending message about surveys
 * 
 * @author zviproject
 *
 */
public class MessageSurveyThread implements Runnable, ISending {

	private Session session;
	private List<SurveyMessage> messages;
	private int emailIndex;
	private String messageTheme;
	private String messageText;
	private String username;
	private ISendingMessage iSendingMessage;
	private String uuid;

	public MessageSurveyThread(Session session, List<SurveyMessage> messages, int emailIndex, String messageTheme,
			String messageText, String username, ISendingMessage iSendingMessage, String uuid) {
		this.session = session;
		this.messages = messages;
		this.emailIndex = emailIndex;
		this.messageTheme = messageTheme;
		this.messageText = messageText;
		this.username = username;
		this.iSendingMessage = iSendingMessage;
		this.uuid = uuid;
	}

	/**
	 * Sending message on email about survey <br>
	 * then insert information in database about
	 */
	@Override
	public void sendMessage() {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(messages.get(emailIndex).getParticipantEmail()));

			message.setSubject(messageTheme);
			message.setText(messageText);

			Transport.send(message);
			iSendingMessage.insertForConfirmVote(uuid, messages.get(emailIndex).getParticipantId(),
					messages.get(emailIndex).getSurveyId());

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		sendMessage();

	}

}
