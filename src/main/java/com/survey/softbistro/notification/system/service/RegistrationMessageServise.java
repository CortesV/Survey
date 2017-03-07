package com.survey.softbistro.notification.system.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.survey.softbistro.notification.system.component.entity.RegistrationMessage;
import com.survey.softbistro.notification.system.component.interfacee.ISendingMessage;
import com.survey.softbistro.notification.system.interfacee.IMessage;
import com.survey.softbistro.notification.system.thrads.MessageClientThread;

/**
 * For createing and sending message that will contain information about new
 * user for confirm registration
 * 
 * @author zviproject
 *
 */
@Service
public class RegistrationMessageServise implements Runnable, IMessage<RegistrationMessage> {
	private Logger log = LogManager.getLogger(getClass());

	/**
	 * Data about account that will sending messages
	 */
	protected static final String USERNAME = "zarovni03@gmail.com";
	protected static final String PASSWORD = "19991904";

	private Properties props;

	private ISendingMessage iSurveyMessage;

	public RegistrationMessageServise(ISendingMessage iSendingMessage) {
		this.iSurveyMessage = iSendingMessage;

		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	@Override
	public void send() {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		List<RegistrationMessage> messages = iSurveyMessage.getEmailOfNewClients();

		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {

			Thread thread = new Thread(new MessageClientThread(session, messages, emailIndex, generateThemeForMessage(),
					generateTextForMessage(messages.get(emailIndex)), USERNAME));
			thread.start();
			log.info(String.format("Register email: %s", messages.get(emailIndex).getClientEmail()));
		}

	}

	@Override
	public String generateTextForMessage(RegistrationMessage client) {
		String urlForVote = String.format("http://localhost:8080/survey/client_name%s", client.getClientName());

		String textMessage = String.format(
				"Registration new account with name \"%s\" \n" + "For confirm click on URL :%s", client.getClientName(),
				urlForVote);
		return textMessage;
	}

	@Override
	public String generateThemeForMessage() {
		return String.format("Registration");
	}

	@Override
	public void run() {
		send();
	}

}
