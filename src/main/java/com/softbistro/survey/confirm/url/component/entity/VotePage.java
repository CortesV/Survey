package com.softbistro.survey.confirm.url.component.entity;

import java.util.List;

/**
 * Response for site with information about question in survey
 * 
 * @author zviproject
 *
 */
public class VotePage {

	private String questionName;

	private Integer required;

	private List<String> questionAnswers;

	private String answerType;

	private Integer requiredComment;

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public List<String> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<String> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public Integer getRequiredComment() {
		return requiredComment;
	}

	public void setRequiredComment(Integer required_comment) {
		this.requiredComment = required_comment;
	}
}
