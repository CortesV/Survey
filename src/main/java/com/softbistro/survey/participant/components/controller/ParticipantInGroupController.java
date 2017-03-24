package com.softbistro.survey.participant.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
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
@RequestMapping("/rest/survey/v1/participant_in_group")
public class ParticipantInGroupController {
	
	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ParticipantInGroupService participantInGroupService;

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return Response
	 */
	@RequestMapping(value = "/group/{group_d}", method = RequestMethod.GET)
	public Response getParticipantsByGroupId(@PathVariable("group_d") Integer groupId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
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
	public Response addParticipantInGroup(@RequestBody ParticipantInGroup participantInGoup, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantInGroupService.addParticipantInGroup(participantInGoup);
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{group_id}/{participant_id}", method = RequestMethod.DELETE)
	public Response deletingParticipantfromGroup(@PathVariable("group_id") Integer groupId,
			@PathVariable("participant_id") Participant participantId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantInGroupService.deletingParticipantfromGroup(groupId, participantId);
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/participant/{participant_id}", method = RequestMethod.GET)
	public Response getParticipantGroups(@PathVariable("participant_id") Integer participantId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantInGroupService.getParticipantGroups(participantId);
	}
}
