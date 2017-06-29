package com.softbistro.survey.notification.db.interfacee;

/**
 * Methods for creating messages for notification
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
	 * @param email
	 */
	public String generateTextForMessage(String mail, String uuid);

}
