package com.softbistro.survey.notification.system.service;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.notification.system.component.entity.SurveyMessage;
import com.softbistro.survey.notification.system.component.interfacee.ISendingMessage;
import com.softbistro.survey.notification.system.interfacee.ICreateMessage;
import com.softbistro.survey.notification.system.threads.MessageSurveyThread;

/**
 * For createing and sending message, that will contain information about survey
 * for participant
 * 
 * @author zviproject
 *
 */
@Service
@Scope("prototype")
public class SurveyMessageService implements Runnable, ICreateMessage<SurveyMessage> {
	private static final Logger log = LogManager.getLogger(SurveyMessageService.class);

	@Autowired
	private ISendingMessage iSendingMessage;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${survey.mail.username}")
	protected String username;

	@Value("${survey.mail.password}")
	protected String password;

	@Value("${survey.text.for.sending.url}")
	protected String url;

	@Autowired
	private Properties propertiesSurvey;

	/**
	 * Sending message from main accaunt to email of users
	 * 
	 * @param toEmail
	 *            - receiver message
	 */
	public void send() {

		Session session = Session.getInstance(propertiesSurvey, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		List<SurveyMessage> messages = iSendingMessage.getEmailsForSending();
		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {

			String uuid = UUID.randomUUID().toString();
			Thread thread = new Thread(new MessageSurveyThread(session, messages, emailIndex, generateThemeForMessage(),
					generateTextForMessage(messages.get(emailIndex), uuid), username, iSendingMessage, uuid));
			thread.start();

			log.info(String.format("Survey email: %s", messages.get(emailIndex).getParticipantEmail()));

		}

	}

	@Override
	public String generateTextForMessage(SurveyMessage message, String uuid) {
		String urlForVote = url + uuid;

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
