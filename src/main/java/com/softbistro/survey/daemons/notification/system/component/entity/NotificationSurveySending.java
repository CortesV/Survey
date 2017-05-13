package com.softbistro.survey.daemons.notification.system.component.entity;

public class NotificationSurveySending {

	public NotificationSurveySending(Integer participantId, Integer surveyId) {
		super();
		this.participantId = participantId;
		this.surveyId = surveyId;
	}

	private String url;
	private Integer participantId;
	private Integer surveyId;

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

	public NotificationSurveySending(String url, Integer participantId, Integer surveyId) {
		super();
		this.url = url;
		this.participantId = participantId;
		this.surveyId = surveyId;
	}
}
