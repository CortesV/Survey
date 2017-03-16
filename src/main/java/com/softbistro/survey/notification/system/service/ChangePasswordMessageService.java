package com.softbistro.survey.notification.system.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;
import com.softbistro.survey.notification.system.component.interfacee.ISendingMessage;
import com.softbistro.survey.notification.system.interfacee.IMessage;
import com.softbistro.survey.notification.system.threads.MessageClientThread;

/**
 * For createing and sending message that will contain information about changed
 * password
 * 
 * @author zviproject
 *
 */
@Service
@Scope("prototype")
public class ChangePasswordMessageService implements Runnable, IMessage<RegistrationMessage> {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	/**
	 * Data about account that will sending messages
	 */
	protected static final String USERNAME = "softbistrosurvey@gmail.com";
	protected static final String PASSWORD = "20170903";

	private Properties props;

	public ChangePasswordMessageService() {
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

		List<RegistrationMessage> messages = iSendingMessage.getEmailOfNewPassword();

		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {
			Thread thread = new Thread(new MessageClientThread(session, messages, emailIndex, generateThemeForMessage(),
					generateTextForMessage(messages.get(emailIndex)), USERNAME));
			thread.start();

			log.info(String.format("Password email: %s", messages.get(emailIndex).getClientEmail()));

		}

	}

	@Override
	public String generateTextForMessage(RegistrationMessage client) {
		String urlForVote = String.format("http://localhost:8080/survey/client_name%s", client.getClientName());

		String textMessage = String.format(
				"Change password on account with name \"%s\" \n" + "For confirm click on URL :%s",
				client.getClientName(), urlForVote);
		return textMessage;
	}

	@Override
	public String generateThemeForMessage() {
		return String.format("Change password");
	}

	@Override
	public void run() {
		send();
	}

}
