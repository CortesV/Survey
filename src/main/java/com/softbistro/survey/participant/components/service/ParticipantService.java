package com.softbistro.survey.participant.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void setParticipant(Participant participant) {

		iParticipant.setParticipant(participant);
	}

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return ResponseEntity
	 */
	public void updateParticipant(Participant participant, Integer id) {

		iParticipant.updateParticipant(participant, id);
	}

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public void deleteParticipantById(Integer participantId) {

		iParticipant.deleteParticipantById(participantId);
	}

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	public Participant getParticipantById(Integer participantId) {

		return iParticipant.getParticipantById(participantId);
	}

	/**
	 * Method to getting participant from db by group Id
	 * 
	 * @param clientid
	 * @return ResponseEntity
	 */
		
	public List<Participant> getParticipantByGroup(Integer groupId) {
		return iParticipant.getParticipantByGroup(groupId);
	}

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attribute value
	 * @return ResponseEntity
	 */
	public List<Participant> getParticipantByAttributeValue(Integer attributeId, String attributeValue) {

		return iParticipant.getParticipantByAttributeValue(attributeId, attributeValue);
	}
	
	/**
	 * Method to getting participant from database by client id
	 * 
	 * @param clientId
	 * @return 
	 */
	public List<Participant> selectClientAllParticipants(Integer clientId) {

		return iParticipant.selectClientAllParticipants(clientId);
	}
}
