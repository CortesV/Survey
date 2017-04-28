package com.softbistro.survey.notification.system.interfacee;

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
