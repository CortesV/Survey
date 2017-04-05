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
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> setParticipant(Participant participant);

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> updateParticipant(Participant participant, Integer id);

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deleteParticipantById(Integer participantId);

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Participant> getParticipantById(Integer participantId);

	/**
	 * Method to getting participant from db by email and client Id
	 * 
	 * @param email, clientId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Participant>> getParticipantByEmailAndClientId(String email, Integer cliectId);
	
	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId, attribute value
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Participant>> getParticipantByAttributeValue(Integer attributeId, String attributeValue);
}
