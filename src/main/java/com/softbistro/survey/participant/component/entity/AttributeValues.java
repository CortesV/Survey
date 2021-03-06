package com.softbistro.survey.participant.component.entity;

import java.io.Serializable;

/**
 * Class that represent attribute values entity
 * 
 * @author af150416
 *
 */
public class AttributeValues implements Serializable {

	/**
	 * Standard value for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * attribute values id variable
	 */
	private Integer id;

	/**
	 * participant id variable
	 */
	private Integer participantId;

	/**
	 * attribute id variable
	 */
	private Integer attributeId;

	/**
	 * attribute values entity value
	 */
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
