package com.survey.softbistro.sending.gmail.interfacee;

import java.util.List;

public interface IEmail {
	/**
	 * Get records from DB with emails of users for sending messages about
	 * survey
	 */
	public List<String> getEmailsForSending();
}
