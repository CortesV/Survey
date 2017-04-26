package com.softbistro.survey.notification.system.interfacee;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;

public interface ICreateMessage<T> {
	/**
	 * Sending message on email
	 * 
	 * @param page
	 */
	public void send();
	
	/**
	 * Generate theme of message
	 * 
	 * @return
	 */
	public String generateThemeForMessage();

	/**
	 * Generate text for message
	 * 
	 * @param email, uuid
	 * @return
	 */
	String generateTextForMessage(String email, String uuid);

}
