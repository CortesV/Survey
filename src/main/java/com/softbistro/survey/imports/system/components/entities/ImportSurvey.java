package com.softbistro.survey.imports.system.components.entities;

import java.util.Date;
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

	private Integer id;

	private Integer clienId;

	private String title;

	private Map<String, Integer> groups;

	private List<Question> questions;

	private Date startTime;

	private Date finishTime;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClienId() {
		return clienId;
	}

	public void setClienId(Integer clienId) {
		this.clienId = clienId;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
