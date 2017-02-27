package com.survey.softbistro.sending.gmail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	private String username;
	private String password;
	private Properties props;

	public EmailSender(String username, String password) {
		this.username = username;
		this.password = password;

		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	public void send(String subject, String text, String fromEmail, String toEmail) {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			// от кого
			message.setFrom(new InternetAddress(username));
			// кому
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			// Заголовок письма
			message.setSubject(subject);
			// Содержимое
			message.setText(text);

			// Отправляем сообщение
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
