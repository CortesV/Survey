package com.softbistro.survey.notification.db.interfacee;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;

/**Methods for creating messages for notification
 * 
 * @author zviproject,alex_alokhin
 *
 */
public interface ICreateMessage {
	
	/**
	 * Sending message to database
	 */
	public void send();
	
	/**
	 * Generate theme of message
	 */
	public String generateThemeForMessage();

	/**
	 * Generate text for message
	 * 
	 * @param email, uuid
	 */
	public String generateTextForMessage(String email);
	
}
