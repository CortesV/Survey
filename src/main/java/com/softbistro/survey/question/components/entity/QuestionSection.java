package com.softbistro.survey.question.components.entity;

import java.io.Serializable;

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
	 * client id
	 */
	private Integer clientId;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
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
}
