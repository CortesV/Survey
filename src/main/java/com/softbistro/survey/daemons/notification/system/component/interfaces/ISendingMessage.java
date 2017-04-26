package com.softbistro.survey.daemons.notification.system.component.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.notification.system.component.entity.RegistrationMessage;
import com.softbistro.survey.notification.system.component.entity.SurveyMessage;
/**
 * Methods for working with notification
 * 
 * @author alex_alokhin
 *
 */
public interface ISendingMessage {
	/**
	 * Get records from DB with e-mails of users, header and body of message 
	 * for sending general information about survey as email
	 */
	public List<Notification> getEmailsForSending();
	
	/**
	 * Get mails of clients that change password 
	 * @return - list of mails
	 * 
	 */
	public ArrayList<String> getEmailOfNewPassword();
	
	/**
	 * Get mails of clients that have registration process
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	public ArrayList<String> getEmailOfNewClients();
	
	/**
	 * Get mails of clients that started the survey
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	
	public ArrayList<String> getEmailsForSendingSurvey();

	/**
	 * Insert notification into table
	 * @param notification - new record
	 * 
	 * @author alex_alokhin
	 */
	public void insertIntoNotification(Notification notification);
	
}
