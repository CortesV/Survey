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

<<<<<<< HEAD:src/main/java/com/survey/softbistro/notification/system/service/ChangePasswordMessageService.java
import com.survey.softbistro.notification.system.component.entity.RegistrationMessage;
import com.survey.softbistro.notification.system.component.interfacee.ISendingMessage;
import com.survey.softbistro.notification.system.interfacee.ICreateMessage;
import com.survey.softbistro.notification.system.threads.MessageClientPasswordThread;
=======
import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;
import com.softbistro.survey.notification.system.component.interfacee.ISendingMessage;
import com.softbistro.survey.notification.system.interfacee.IMessage;
import com.softbistro.survey.notification.system.threads.MessageClientThread;
>>>>>>> 61b3f919c64e2ad59f500faeec5fa96576ba436f:src/main/java/com/softbistro/survey/notification/system/service/ChangePasswordMessageService.java

/**
 * For createing and sending message that will contain information about changed
 * password
 * 
 * @author zviproject
 *
 */
@Service
@Scope("prototype")
public class ChangePasswordMessageService implements Runnable, ICreateMessage<RegistrationMessage> {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ISendingMessage iSendingMessage;

	/**
	 * Data about account that will sending messages
	 */
	@Value("${password.mail.username}")
	protected String username;

	@Value("${password.mail.password}")
	protected String password;

	@Autowired
	private Properties propertiesPassword;

	@Override
	public void send() {
		Session session = Session.getInstance(propertiesPassword, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		List<RegistrationMessage> messages = iSendingMessage.getEmailOfNewPassword();

		for (int emailIndex = 0; emailIndex < messages.size(); emailIndex++) {
			String uuid = UUID.randomUUID().toString();
			Thread thread = new Thread(
					new MessageClientPasswordThread(session, messages, emailIndex, generateThemeForMessage(),
							generateTextForMessage(messages.get(emailIndex), uuid), username, iSendingMessage, uuid));
			thread.start();

			log.info(String.format("Password email: %s", messages.get(emailIndex).getClientEmail()));

		}

	}

	@Override
	public String generateTextForMessage(RegistrationMessage client, String uuid) {
		String urlForVote = String.format("http://localhost:8080/survey/client_name%s", uuid);

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
