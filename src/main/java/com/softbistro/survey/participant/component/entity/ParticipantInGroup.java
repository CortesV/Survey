package com.softbistro.survey.participant.component.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Class that represents connection between Participant entity and Group entity
 * 
 * @author af150416
 *
 */
public class ParticipantInGroup implements Serializable {

	/**
	 * Standard variable for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * participantIngroupId
	 */
	private Integer id;

	/**
	 * participantId
	 * 
	 */
	private Integer participantId;
	
	private List<Integer> participantsId;

	/**
	 * groupId
	 */
	private Integer groupId;

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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public List<Integer> getParticipantsId() {
		return participantsId;
	}

	public void setParticipantsId(List<Integer> participantsId) {
		this.participantsId = participantsId;
	}
	
}
