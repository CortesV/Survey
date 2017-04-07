package com.softbistro.survey.creating.survey.component.entity;

/**
 * Class that represent group entity
 * 
 * @author af150416
 *
 */
public class Group {

	private Integer id;

	private Integer clientId;

	private Integer surveyId;

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
