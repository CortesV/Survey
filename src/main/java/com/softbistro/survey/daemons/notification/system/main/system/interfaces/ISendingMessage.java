package com.softbistro.survey.daemons.notification.system.main.system.interfaces;

/**
 * For work with messages: - sending message;
 */
public interface ISendingMessage {
	/**
	 * For sending message in separate thread. Try sending message on email for
	 * everyone message in separate thread.
	 */
	public void sendMessage();
}
