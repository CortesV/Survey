package com.softbistro.survey.participant.components.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.participant.components.service.ParticipantInGroupService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for participantInGroup entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/participant_in_group")
public class ParticipantInGroupController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ParticipantInGroupService participantInGroupService;

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Participants By Group", notes = "Get participants by group id", tags = "Participant")
	@RequestMapping(value = "/group/{group_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Participant>> getParticipantsByGroupId(@PathVariable("group_id") Integer groupId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Participant>>(HttpStatus.UNAUTHORIZED);
		}

		return participantInGroupService.getParticipantsByGroupId(groupId);
	}

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Add Participant in Group", notes = "Add participant in group by group id and group id", tags = "Participant")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> addParticipantInGroup(@RequestBody ParticipantInGroup participantInGoup,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return participantInGroupService.addParticipantInGroup(participantInGoup);
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete Participants from Group", notes = "Delete participants from group by group id and participant id", tags = "Participant")
	@RequestMapping(value = "/{group_id}/{participant_id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletingParticipantfromGroup(@PathVariable("group_id") Integer groupId,
			@PathVariable("participant_id") Participant participantId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return participantInGroupService.deletingParticipantfromGroup(groupId, participantId);
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Participant Groups", notes = "Get participant groups by participant id", tags = "Participant")
	@RequestMapping(value = "/participant/{participant_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Group>> getParticipantGroups(@PathVariable("participant_id") Integer participantId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Group>>(HttpStatus.UNAUTHORIZED);
		}

		return participantInGroupService.getParticipantGroups(participantId);
	}
}
