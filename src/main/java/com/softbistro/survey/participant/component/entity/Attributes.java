package com.softbistro.survey.participant.component.entity;

import java.io.Serializable;

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
}
