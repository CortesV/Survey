package com.softbistro.survey.daemons.notification.system.component.interfaces;

import java.util.List;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;

public interface ISendingMessage {
	/**
	 * Get records from DB with e-mails of users, header and body of message 
	 * for sending general information about survey as email
	 */
	public List<Notification> getEmailsForSending();
}
