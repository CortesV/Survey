package com.softbistro.survey.participant.components.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.participant.components.entity.Participant;

/**
 * Interface for participant entity
 * 
 * @author af150416
 *
 */
public interface IParticipant {

	/**
	 * Method for creating participant
	 * 
	 * @param participant
	 * @return
	 */
	public void setParticipant(Participant participant);

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return
	 */
	public void updateParticipant(Participant participant, Integer id);

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return
	 */
	public void deleteParticipantById(Integer participantId);

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return
	 */
	public Participant getParticipantById(Integer participantId);

	/**
	 * Method to getting participant from db by group Id
	 * 
	 * 
	 * @param clientId
	 * @return
	 */

	public List<Participant> getParticipantByGroup(Integer groupId);

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attribute value
	 * @return
	 */
	public List<Participant> getParticipantByAttributeValue(Integer attributeId, String attributeValue);

	/**
	 * Method to getting participant from database by client id
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Participant> selectClientAllParticipants(Integer cliectId);
}
