package com.survey.softbistro.notification.system.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.survey.softbistro.notification.system.component.entity.SurveyMessage;
import com.survey.softbistro.notification.system.component.interfacee.ISendingMessage;
import com.survey.softbistro.notification.system.interfacee.IMessage;
import com.survey.softbistro.notification.system.thrads.MessageSurveyThread;

/**
 * For createing and sending message, that will contain information about survey
 * for participant
 * 
 * @author zviproject
 *
 */
@Service
public class SurveyMessageService implements Runnable, IMessage<SurveyMessage> {
	protected static final String USERNAME = "zarovni03@gmail.com";
	protected static final String PASSWORD = "19991904";
	protected Properties props;
	private static final Logger log = LogManager.getLogger(SurveyMessageService.class);

	private ISendingMessage iSendingMessage;

	public SurveyMessageService(ISendingMessage iSendingMessage) {
		this.iSendingMessage = iSendingMessage;

		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	/**
	 * Sending message from main accaunt to email of users
	 * 
	 * @param toEmail
	 *            - receiver message
	 */
	public void send() {

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		List<SurveyMessage> messages = iSendingMessage.getEmailsForSending();

		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {

			Thread thread = new Thread(new MessageSurveyThread(session, messages, emailIndex, generateThemeForMessage(),
					generateTextForMessage(messages.get(emailIndex)), USERNAME));
			thread.start();

			log.info(String.format("Survey email: %s", messages.get(emailIndex).getParticipantEmail()));

		}

	}

	@Override
	public String generateTextForMessage(SurveyMessage message) {
		String urlForVote = String.format("http://localhost:8080/survey/survey_id%d/participant_id%d",
				message.getSurveyId(), message.getParticipantId());

		String textMessage = String.format(
				"Survey with name \"%s\" by \"%s\" was created.\n Survey will be from \" %tD \" to \" %tD \" \n"
						+ "You can vote by clicking on URL : %s",
				message.getSurveyName(), message.getClientName(), message.getSurveyStartTime(),
				message.getSurveyFinashTime(), urlForVote);
		return textMessage;
	}

	@Override
	public String generateThemeForMessage() {
		return String.format("Survey");
	}

	@Override
	public void run() {
		send();
	}

}
