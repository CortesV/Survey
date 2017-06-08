package com.softbistro.survey.daemons.notification.system.main.system.interfaces;

/**
 * For sending messages
 * 
 */
public interface ISendingMessages {
	/**
	 * Creating thread for everyone message. Start to sending messages in
	 * separate thread.
	 */
	public void send();
}
