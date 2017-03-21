package com.softbistro.survey.question.components.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * Class that represents question section entity;
 * 
 * @author af150416
 *
 */
public class QuestionSection implements Serializable {

	/**
	 * standard value for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * participant id
	 */
	private Integer id;

	/**
	 * survey id
	 */
	private Integer surveyId;

	/**
	 * section name
	 */
	private String sectionName;

	/**
	 * description short
	 */
	private String descriptionShort;

	/**
	 * description long
	 */
	private String descriptionLong;

	/**
	 * participant created date
	 */
	private Date createdDate;

	/**
	 * participant modified date
	 */
	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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

	public void setDescriptionLong(String descrioptionLong) {
		this.descriptionLong = descrioptionLong;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
