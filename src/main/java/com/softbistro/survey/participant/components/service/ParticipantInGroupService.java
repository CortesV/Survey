package com.softbistro.survey.participant.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.participant.components.interfaces.IParticipantInGroup;

/**
 * Service for ParticipantInGroup entity
 * 
 * @author af150416
 *
 */
@Service
public class ParticipantInGroupService {

	@Autowired
	private IParticipantInGroup iparticipantInGroup;

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Participant>> getParticipantsByGroupId(Integer groupId) {
		return iparticipantInGroup.getParticipantsByGroup(groupId);
	}

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> addParticipantInGroup(ParticipantInGroup participantInGoup) {
		return iparticipantInGroup.addParticipantInGroup(participantInGoup);
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deletingParticipantfromGroup(Integer groupId, Participant participantId) {
		return iparticipantInGroup.deletingParticipantfromGroup(groupId, participantId);
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Group>> getParticipantGroups(Integer participantId) {
		return iparticipantInGroup.getParticipantGroups(participantId);
	}
}
