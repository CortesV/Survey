package com.softbistro.survey.participant.components.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.participant.components.interfaces.IParticipantInGroup;
import com.softbistro.survey.response.Response;

/**
 * Service for ParticipantInGroup entity
 * 
 * @author af150416
 *
 */
@Service
public class ParticipantInGroupService {

	@Autowired
	private IParticipantInGroup iparticipantInGroup;

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return Response
	 */
	public Response getParticipantsByGroupId(Integer groupId) {
		return iparticipantInGroup.getParticipantsByGroup(groupId);
	}

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	public Response addParticipantInGroup(ParticipantInGroup participantInGoup) {
		return iparticipantInGroup.addParticipantInGroup(participantInGoup);
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	public Response deletingParticipantfromGroup(Integer groupId, Participant participantId) {
		return iparticipantInGroup.deletingParticipantfromGroup(groupId, participantId);
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return Response
	 */
	public Response getParticipantGroups(Integer participantId) {
		return iparticipantInGroup.getParticipantGroups(participantId);
	}
}
