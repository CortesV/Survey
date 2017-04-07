package com.softbistro.survey.participant.components.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;

/**
 * Interface for participaantInGroup entity
 * 
 * @author af150416
 *
 */
public interface IParticipantInGroup {

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> addParticipantInGroup(ParticipantInGroup participantInGoup);

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deletingParticipantfromGroup(Integer groupId, Integer participantId);

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Participant>> getParticipantsByGroup(Integer groupId);

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Group>> getParticipantGroups(Integer participantId);
}
