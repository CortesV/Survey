package com.softbistro.survey.participant.controller;

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
import com.softbistro.survey.participant.component.entity.Group;
import com.softbistro.survey.participant.component.entity.Participant;
import com.softbistro.survey.participant.component.entity.ParticipantInGroup;
import com.softbistro.survey.participant.service.ParticipantInGroupService;

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
	@RequestMapping(value = "/group/{group_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Participant>> getParticipantsByGroupId(@PathVariable("group_id") Integer groupId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(participantInGroupService.getParticipantsByGroupId(groupId), HttpStatus.OK);
	}

	/**
	 * Method for adding participants in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Add Participants in Group", notes = "Add participants in group by list participants id and group id", tags = "Participant")
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addParticipantInGroup(@RequestBody ParticipantInGroup participantInGoup,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		participantInGroupService.addParticipantsInGroup(participantInGoup);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete Participants from Group", notes = "Delete participants from group by group id and participant id", tags = "Participant")
	@RequestMapping(value = "/{group_id}/{participant_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deletingParticipantfromGroup(@PathVariable("group_id") Integer groupId,
			@PathVariable("participant_id") Integer participantId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		participantInGroupService.deletingParticipantfromGroup(groupId, participantId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Participant Groups", notes = "Get participant groups by participant id", tags = "Participant")
	@RequestMapping(value = "/participant/{participant_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Group>> getParticipantGroups(@PathVariable("participant_id") Integer participantId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(participantInGroupService.getParticipantGroups(participantId), HttpStatus.OK);
	}
}
