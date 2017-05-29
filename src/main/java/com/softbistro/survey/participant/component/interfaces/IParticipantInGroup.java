package com.softbistro.survey.participant.component.interfaces;

import java.util.List;

import com.softbistro.survey.participant.component.entity.Group;
import com.softbistro.survey.participant.component.entity.Participant;
import com.softbistro.survey.participant.component.entity.ParticipantInGroup;

/**
 * Interface for participaantInGroup entity
 * 
 * @author af150416
 *
 */
public interface IParticipantInGroup {

	/**
	 * Method for adding participants in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public void addParticipantsInGroup(ParticipantInGroup participantInGoup);

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public void deletingParticipantfromGroup(Integer groupId, Integer participantId);

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public List<Participant> getParticipantsByGroup(Integer groupId);

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public List<Group> getParticipantGroups(Integer participantId);

}
