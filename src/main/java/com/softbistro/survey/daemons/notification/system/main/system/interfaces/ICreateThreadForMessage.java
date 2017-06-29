package com.softbistro.survey.daemons.notification.system.main.system.interfaces;

/**
 * For work with messages: - create threads for everyone message;
 */
public interface ICreateThreadForMessage {
	/**
	 * Creating thread for everyone message. Start to sending messages in
	 * separate thread.
	 */
	public void createThreadForMessage();
}
