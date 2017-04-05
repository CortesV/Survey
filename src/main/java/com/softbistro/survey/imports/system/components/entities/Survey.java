package com.softbistro.survey.imports.system.components.entities;

import java.util.List;

/**
 * Entity of survey.
 * 
 * @author olegnovatskiy
 *
 */
public class Survey {

	private Long id;

	private Long clienId;

	private String title;

	private List<GroupQuestions> groupQuestions;

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

	public List<GroupQuestions> getGroupQuestions() {
		return groupQuestions;
	}

	public void setGroupQuestions(List<GroupQuestions> groupQuestions) {
		this.groupQuestions = groupQuestions;
	}

}
