package com.softbistro.survey.participant.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.participant.components.service.ParticipantInGroupService;
import com.softbistro.survey.response.Response;

/**
 * Controller for participantInGroup entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/participantInGroup")
public class ParticipantInGroupController {

	@Autowired
	ParticipantInGroupService participantInGroupService;

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return Response
	 */
	@RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
	public Response getParticipantsByGroupId(@PathVariable Integer groupId) {
		return participantInGroupService.getParticipantsByGroupId(groupId);
	}

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response addParticipantInGroup(@RequestBody ParticipantInGroup participantInGoup) {
		return participantInGroupService.addParticipantInGroup(participantInGoup);
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{groupId}/{participantId}", method = RequestMethod.DELETE)
	public Response deletingParticipantfromGroup(@PathVariable Integer groupId, Participant participantId) {
		return participantInGroupService.deletingParticipantfromGroup(groupId, participantId);
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/participant/{participantId}", method = RequestMethod.GET)
	public Response getParticipantGroups(Integer participantId) {
		return participantInGroupService.getParticipantGroups(participantId);
	}
}
