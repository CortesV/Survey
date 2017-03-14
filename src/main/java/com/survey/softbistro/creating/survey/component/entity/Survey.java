package com.survey.softbistro.creating.survey.component.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity for survey. <br>
 * It will use in saving to database new survey
 * 
 * @author zviproject
 *
 */
public class Survey {

	private Integer id;

	@JsonProperty(value = "client_id")
	private Integer clientId;

	@JsonProperty(value = "survey_name")
	private String surveyName;

	@JsonProperty(value = "survey_theme")
	private String surveyTheme;

	@JsonProperty(value = "start_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private Date startTime;

	@JsonProperty(value = "finish_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private Date finishTime;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getSurveyTheme() {
		return surveyTheme;
	}

	public void setSurveyTheme(String surveyTheme) {
		this.surveyTheme = surveyTheme;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
