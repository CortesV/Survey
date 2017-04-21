package com.softbistro.survey.imports.system.components.entities;

import java.util.List;
import java.util.Map;

import com.softbistro.survey.question.components.entity.Question;

/**
 * Entity of survey.
 * 
 * @author olegnovatskiy
 *
 */
public class ImportSurvey {

	private Long id;

	private Long clienId;

	private String title;

	private Map<String, Integer> groups;

	private List<Question> questions;

	public Long getClienId() {
		return clienId;
	}

	public void setClienId(Long clienId) {
		this.clienId = clienId;
	}

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

	public Map<String, Integer> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, Integer> groups) {
		this.groups = groups;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
