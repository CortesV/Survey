package com.survey.softbistro.notification.system.interfacee;

public interface ICreateMessage<T> {
	/**
	 * Sending message on email
	 * 
	 * @param page
	 */
	public void send();

	/**
	 * Generate text in message
	 * 
	 * @param message
	 * @return
	 */
	public String generateTextForMessage(T message, String uuid);

	/**
	 * Generate theme of message
	 * 
	 * @return
	 */
	public String generateThemeForMessage();

}
