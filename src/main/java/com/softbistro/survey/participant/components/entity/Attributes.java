package com.softbistro.survey.participant.components.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Class that represent attributes entity
 * 
 * @author af150416
 *
 */
public class Attributes implements Serializable {

	/**
	 * standard value for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * attributes id
	 */
	private Integer id;

	/**
	 * group id
	 */
	private Integer groupId;

	/**
	 * attribute name
	 */
	private String attribute;

	/**
	 * attribute created date
	 */
	private Date createdDate;

	/**
	 * attribute modified date
	 */
	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
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
