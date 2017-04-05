package com.softbistro.survey.imports.system.components.entities;

import java.util.List;

/**
 * Entity for group of questions. 
 * 
 * @author olegnovatskiy
 *
 */
public class GroupQuestions {

	private Long id;
	
	private String title;
	
	private List<Question> questions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
