package com.survey.softbistro.sending.gmail.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.softbistro.response.ResponseStatus;
import com.survey.softbistro.sending.gmail.component.entity.SurveyMessage;
import com.survey.softbistro.sending.gmail.component.interfacee.ISurveyMessage;

/**
 * Sending message to users
 * 
 * @author zviproject
 *
 */

@Service
public class EmailSenderService {
	private static final String USERNAME = "zarovni03@gmail.com";
	private static final String PASSWORD = "19991904";
	private Properties props;

	@Autowired
	ISurveyMessage iSurveyMessage;

	public EmailSenderService() {
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
	public ResponseStatus send() {
		try {
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});

			List<SurveyMessage> messages = iSurveyMessage.getEmailsForSending(0);

			for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(USERNAME));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(messages.get(emailIndex).getParticipantEmail()));
				message.setSubject(generateThemeForMessage());
				message.setText(generateTextForMessage(messages.get(emailIndex).getSurveyName(),
						messages.get(emailIndex).getClientName(), messages.get(emailIndex).getSurveyId(),
						messages.get(emailIndex).getParticipantId()));

				Transport.send(message);

			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		return ResponseStatus.DONE;
	}

	private String generateTextForMessage(String surveyName, String clientName, Integer surveyId,
			Integer participantId) {
		String urlForVote = String.format("https://trainee.atlassian.net/%s",
				DigestUtils.md5Hex(String.format("survey_id%d/participant_id%d", surveyId, participantId)));

		String textMessage = String.format("Survey with name \"%s\" by \"%s\" was created.\n", surveyName, clientName)
				+ String.format("You can vote by clicking on URL: %s", urlForVote);
		return textMessage;
	}

	private String generateThemeForMessage() {
		return String.format("Survey");
	}

}
