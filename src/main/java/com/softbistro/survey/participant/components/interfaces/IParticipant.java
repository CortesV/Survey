package com.softbistro.survey.participant.components.interfaces;

import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.response.Response;

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
	 * @return Response
	 */
	public Response setParticipant(Participant participant);

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	public Response updateParticipant(Participant participant);

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	public Response deleteParticipantById(Integer participantId);

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	public Response getParticipantById(Integer participantId);
}
