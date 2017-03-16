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
}
