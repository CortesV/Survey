package com.softbistro.survey.participant.components.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Class that represent group entity
 * 
 * @author af150416
 *
 */
public class Group implements Serializable {

	/**
	 * standard value for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * group id
	 */
	private Integer id;

	/**
	 * client id
	 */
	private Integer clientId;

	/**
	 * group name
	 */
	private String groupName;

	/**
	 * group created date
	 */
	private Date createdDate;

	/**
	 * group modified date
	 */
	private Date modifiedDate;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
