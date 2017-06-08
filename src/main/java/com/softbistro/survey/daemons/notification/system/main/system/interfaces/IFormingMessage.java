package com.softbistro.survey.daemons.notification.system.main.system.interfaces;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface IFormingMessage {

	public void formingMessage() throws AddressException, MessagingException;

}
