package com.softbistro.survey.notification.db.interfacee;

/**Methods for creating messages for notification
 * 
 * @author zviproject,alex_alokhin
 *
 * @param <T>
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
	public String generateTextForMessage(String email, String uuid);

}
