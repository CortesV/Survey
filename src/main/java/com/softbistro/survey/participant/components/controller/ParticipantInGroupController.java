package com.softbistro.survey.participant.components.controller;

import java.util.List;

import org.apache.log4j.Logger;
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

	private static final Logger LOGGER = Logger.getLogger(ParticipantInGroupController.class);

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

		try {

			return new ResponseEntity<>(participantInGroupService.getParticipantsByGroupId(groupId), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Add Participant in Group", notes = "Add participant in group by group id and group id", tags = "Participant")
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addParticipantInGroup(@RequestBody ParticipantInGroup participantInGoup,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {
			
			participantInGroupService.addParticipantInGroup(participantInGoup);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

		try {

			participantInGroupService.deletingParticipantfromGroup(groupId, participantId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

		try {

			return new ResponseEntity<>(participantInGroupService.getParticipantGroups(participantId), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
