package com.softbistro.survey.imports.system.components.entities;

public class Question {

	private Long id;

	private Long surveyId;

	private Long questionSectionId;

	private String text;

	private String answerType;

	private String questionChoices;

	private Boolean required;

	private Boolean requiredComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getQuestionSectionId() {
		return questionSectionId;
	}

	public void setQuestionSectionId(Long questionSectionId) {
		this.questionSectionId = questionSectionId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getRequiredComment() {
		return requiredComment;
	}

	public void setRequiredComment(Boolean requiredComment) {
		this.requiredComment = requiredComment;
	}

}
