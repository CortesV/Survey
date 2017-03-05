package com.survey.softbistro.sending.gmail.component.entity;

import java.util.Date;

/**
 * Entity for structure of email
 * 
 * @author zviproject
 *
 */
public class SurveyMessage {
	private String clientName;
	private String surveyName;
	private String participantEmail;

	private Integer participantId;
	private Integer surveyId;

	private Date surveyStartTime;
	private Date surveyFinashTime;

	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getParticipantEmail() {
		return participantEmail;
	}

	public void setParticipantEmail(String participantEmail) {
		this.participantEmail = participantEmail;
	}

	public Date getSurveyFinashTime() {
		return surveyFinashTime;
	}

	public void setSurveyFinashTime(Date surveyFinashTime) {
		this.surveyFinashTime = surveyFinashTime;
	}

	public Date getSurveyStartTime() {
		return surveyStartTime;
	}

	public void setSurveyStartTime(Date surveyStartTime) {
		this.surveyStartTime = surveyStartTime;
	}

}
