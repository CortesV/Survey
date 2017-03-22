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

	/**
	 * Method to getting participant from db by email
	 * 
	 * @param email
	 * @return Response
	 */
	public Response getParticipantByEmail(String email);
	
	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId, attribute value
	 * @return Response
	 */
	public Response getParticipantByAttributeValue(Integer attributeId, String attributeValue);
}
