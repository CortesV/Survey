package com.softbistro.survey.confirm.url.component.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contain information about answer
 * 
 * @author zviproject
 *
 */
public class Answer {
	@JsonProperty(value = "answer_type")
	private String answerType;

	@JsonProperty(value = "answer_value")
	private String answerValue;

	@JsonProperty(value = "comment")
	private String comment;

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
