package com.softbistro.survey.participant.components.interfaces;

import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.response.Response;

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
	 * @return Response
	 */
	public Response addParticipantInGroup(ParticipantInGroup participantInGoup);

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	public Response deletingParticipantfromGroup(Integer groupId, Participant participantId);

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return Response
	 */
	public Response getParticipantsByGroup(Integer groupId);

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return Response
	 */
	public Response getParticipantGroups(Integer participantId);
}
