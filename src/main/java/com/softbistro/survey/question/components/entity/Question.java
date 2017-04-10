package com.softbistro.survey.question.components.entity;

/**
 * Simple JavaBean bject that represents a Question
 * 
 * @author cortes
 *
 */
public class Question {
	
	private Integer id;
	
	private Integer surveyId;
	
	private String question;
	
	private String descriptionShort;
	
	private String descriptionLong;
	
	private Integer questionSectionId;
	
	private String answerType;
	
	private String questionChoices;
	
	private Boolean required;
	
	private Boolean requiredComment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer survey_id) {
		this.surveyId = survey_id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public String getDescriptionLong() {
		return descriptionLong;
	}

	public void setDescriptionLong(String descriptionLong) {
		this.descriptionLong = descriptionLong;
	}

	public Integer getQuestionSectionId() {
		return questionSectionId;
	}

	public void setQuestionSectionId(Integer questionSectionId) {
		this.questionSectionId = questionSectionId;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public String getQuestionChoices() {
		return questionChoices;
	}

	public void setQuestionChoices(String questionChoices) {
		this.questionChoices = questionChoices;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Boolean isRequiredComment() {
		return requiredComment;
	}

	public void setRequiredComment(Boolean requiredComment) {
		this.requiredComment = requiredComment;
	}
}
