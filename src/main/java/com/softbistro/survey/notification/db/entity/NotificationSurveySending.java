package com.softbistro.survey.notification.db.entity;

import java.util.Date;

/**
 * Entity for sending_survey table
 * 
 * @author alex_alokhin
 *
 */
public class NotificationSurveySending {
	private String url;
	private Integer participantId;
	private Integer surveyId;
	private String email;
	private Date date;

	public NotificationSurveySending(Integer participantId, Integer surveyId, String email) {
		this.participantId = participantId;
		this.surveyId = surveyId;
		this.email = email;
	}

	public NotificationSurveySending(String url, Integer participantId, Integer surveyId, Date date) {
		this.url = url;
		this.participantId = participantId;
		this.surveyId = surveyId;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public NotificationSurveySending(String url, Integer participantId, Integer surveyId, String email) {
		this.url = url;
		this.participantId = participantId;
		this.surveyId = surveyId;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

}
