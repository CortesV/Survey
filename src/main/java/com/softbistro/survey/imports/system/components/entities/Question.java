package com.softbistro.survey.imports.system.components.entities;

import java.util.List;

import com.softbistro.survey.imports.system.components.enums.TypeQuestion;

public class Question {

	private Long id;
	private String value;
	private String type;
	private String[] answers;
	private Boolean required;
	private Boolean comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getComment() {
		return comment;
	}

	public void setComment(Boolean comment) {
		this.comment = comment;
	}

}
