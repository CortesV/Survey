package com.survey.softbistro.components.entity;

/**
 * Simple JavaBean bject that represents a Question
 * 
 * @author cortes
 *
 */
public class Question {

	private Long id;
	private Long surveyId;
	private String question;
	private String descriptionShort;
	private String descriptionLong;
	private Long questionSectionId;
	private String answerType;
	private String questionChoices;
	private boolean required;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long survey_id) {
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

	public Long getQuestionSectionId() {
		return questionSectionId;
	}

	public void setQuestionSectionId(Long questionSectionId) {
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

}
