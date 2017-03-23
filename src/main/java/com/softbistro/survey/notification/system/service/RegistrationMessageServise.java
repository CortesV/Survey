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

import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;
import com.softbistro.survey.notification.system.component.interfacee.ISendingMessage;
import com.softbistro.survey.notification.system.interfacee.ICreateMessage;
import com.softbistro.survey.notification.system.threads.MessageClientEmailThread;

/**
 * For createing and sending message that will contain information about new
 * user for confirm registration
 * 
 * @author zviproject
 *
 */
@Service
@Scope("prototype")
public class RegistrationMessageServise implements Runnable, ICreateMessage<RegistrationMessage> {
	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSurveyMessage;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${client.mail.username}")
	protected String username;

	@Value("${client.mail.password}")
	protected String password;

	@Value("${client.text.for.sending.url}")
	String url;

	@Autowired
	private Properties propertiesClient;

	@Override
	public void send() {
		Session session = Session.getInstance(propertiesClient, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		List<RegistrationMessage> messages = iSurveyMessage.getEmailOfNewClients();

		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {
			String uuid = UUID.randomUUID().toString();
			Thread thread = new Thread(
					new MessageClientEmailThread(session, messages, emailIndex, generateThemeForMessage(),
							generateTextForMessage(messages.get(emailIndex), uuid), username, iSurveyMessage, uuid));
			thread.start();
			log.info(String.format("Register email: %s", messages.get(emailIndex).getClientEmail()));
		}

	}

	@Override
	public String generateTextForMessage(RegistrationMessage client, String uuid) {
		String urlForVote = url + uuid;

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
