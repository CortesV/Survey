package com.softbistro.survey.creating.survey.component.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Group {

	@JsonProperty(value = "group_id")
	private Integer id;

	@JsonProperty(value = "client_id")
	private Integer clientId;

	@JsonProperty(value = "survey_id")
	private Integer surveyId;

	@JsonProperty(value = "group_name")
	private String groupName;

	public Integer getId() {
		return id;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
