package com.survey.softbistro.notification.system.interfacee;

public interface IMessage<T> {
	/**
	 * Sending message on email
	 * 
	 * @param page
	 */
	public void send(int page);

	/**
	 * Generate text in message
	 * 
	 * @param message
	 * @return
	 */
	public String generateTextForMessage(T message);

	/**
	 * Generate theme of message
	 * 
	 * @return
	 */
	public String generateThemeForMessage();

}
