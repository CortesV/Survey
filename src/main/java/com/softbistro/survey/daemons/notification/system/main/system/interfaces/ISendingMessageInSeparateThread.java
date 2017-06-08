package com.softbistro.survey.daemons.notification.system.main.system.interfaces;

/**
 * For sending message in separate thread
 * 
 */
public interface ISendingMessageInSeparateThread {
	/**
	 * Try sending message on email for everyone message in separate thread
	 */
	public void sendMessage();
}
