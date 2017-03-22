package com.softbistro.survey.participant.components.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.interfaces.IParticipant;
import com.softbistro.survey.response.Response;

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
	 * @return Response
	 */
	public Response setParticipant(Participant participant) {
		return iParticipant.setParticipant(participant);
	}

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	public Response updateParticipant(Participant participant) {
		return iParticipant.updateParticipant(participant);
	}

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	public Response deleteParticipantById(Integer participantId) {
		return iParticipant.deleteParticipantById(participantId);
	}

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	public Response getParticipantById(Integer participantId) {
		return iParticipant.getParticipantById(participantId);
	}

	/**
	 * Method to getting participant from db by email and client Id
	 * 
	 * @param email,
	 *            clientid
	 * @return Response
	 */
	public Response getParticipantByEmailAndClientId(String email, Integer clientid) {
		return iParticipant.getParticipantByEmailAndClientId(email, clientid);
	}

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attribute value
	 * @return Response
	 */
	public Response getParticipantByAttributeValue(Integer attributeId, String attributeValue) {
		return iParticipant.getParticipantByAttributeValue(attributeId, attributeValue);
	}
}
