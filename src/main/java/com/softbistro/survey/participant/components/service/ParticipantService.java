package com.softbistro.survey.participant.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.interfaces.IParticipant;

/**
 * Service for participant entity
 * 
 * @author af150416
 *
 */
@Service
public class ParticipantService {

	@Autowired
	private IParticipant iParticipant;

	/**
	 * Method for creating participant
	 * 
	 * @param participant
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> setParticipant(Participant participant) {
		return iParticipant.setParticipant(participant);
	}

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> updateParticipant(Participant participant, Integer id) {
		return iParticipant.updateParticipant(participant, id);
	}

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deleteParticipantById(Integer participantId) {
		return iParticipant.deleteParticipantById(participantId);
	}

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Participant> getParticipantById(Integer participantId) {
		return iParticipant.getParticipantById(participantId);
	}

	/**
	 * Method to getting participant from db by email and client Id
	 * 
	 * @param email,
	 *            clientid
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Participant>> getParticipantByEmailAndClientId(String email, Integer clientid) {
		return iParticipant.getParticipantByEmailAndClientId(email, clientid);
	}

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attribute value
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Participant>> getParticipantByAttributeValue(Integer attributeId,
			String attributeValue) {
		return iParticipant.getParticipantByAttributeValue(attributeId, attributeValue);
	}
}
