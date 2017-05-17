package com.softbistro.survey.imports.system.components.entities;

public class Question {

	private Long id;
	private Long survey_id;
	private Long question_section_id;
	private String question;
	private String answer_type;
	private String[] answersList;
	private String question_choices;
	private Boolean required;
	private Boolean required_comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurvey_id() {
		return survey_id;
	}

	public void setSurvey_id(Long survey_id) {
		this.survey_id = survey_id;
	}

	public Long getQuestion_section_id() {
		return question_section_id;
	}

	public void setQuestion_section_id(Long question_section_id) {
		this.question_section_id = question_section_id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer_type() {
		return answer_type;
	}

	public void setAnswer_type(String answer_type) {
		this.answer_type = answer_type;
	}

	public String[] getAnswersList() {
		return answersList;
	}

	public void setAnswersList(String[] answersList) {
		this.answersList = answersList;
	}

	public String getQuestion_choices() {
		return question_choices;
	}

	public void setQuestion_choices(String question_choices) {
		this.question_choices = question_choices;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getRequired_comment() {
		return required_comment;
	}

	public void setRequired_comment(Boolean required_comment) {
		this.required_comment = required_comment;
	}

}
