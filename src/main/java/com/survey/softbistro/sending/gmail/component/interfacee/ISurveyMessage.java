package com.survey.softbistro.sending.gmail.component.interfacee;

import java.util.List;

import com.survey.softbistro.sending.gmail.component.entity.SurveyMessage;

public interface ISurveyMessage {
	/**
	 * Get records from DB with emails of users for sending messages about
	 * survey
	 */
	public List<SurveyMessage> getEmailsForSending(Integer page);
}
