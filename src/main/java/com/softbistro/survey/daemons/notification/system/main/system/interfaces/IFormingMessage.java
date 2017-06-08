package com.softbistro.survey.daemons.notification.system.main.system.interfaces;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * For work with messages: - forming message;
 */
public interface IFormingMessage {

	/**
	 * Forming message for email that will be sent then
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public void formingMessage() throws AddressException, MessagingException;

}
