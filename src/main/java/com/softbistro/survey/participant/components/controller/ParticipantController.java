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
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.service.ParticipantService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for participant entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/participant")
public class ParticipantController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ParticipantService participantService;

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Participant By Id", notes = "Get participant instanse by participant id", tags = "Participant")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Participant> getParticipantById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Participant>(HttpStatus.UNAUTHORIZED);
		}

		return participantService.getParticipantById(id);
	}

	/**
	 * Method to getting participant from db by email and client Id
	 * 
	 * @param email,
	 *            clientid
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Participants By Client", notes = "Get participant instanse by participant email and client id", tags = "Participant")
	@RequestMapping(value = "email/{email}/{client_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Participant>> getParticipantByEmailAndClientId(@PathVariable("email") String email,
			@PathVariable("client_id") Integer clientid, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Participant>>(HttpStatus.UNAUTHORIZED);
		}

		return participantService.getParticipantByEmailAndClientId(email, clientid);
	}

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attribute value
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Participant By Attribute Value", notes = "Get participant instanse by attribute id and attribute value", tags = "Participant")
	@RequestMapping(value = "attribute/{attribute_id}/{attribute_value}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Participant>> getParticipantByAttributeValue(
			@PathVariable("attribute_id") Integer attributeId, @PathVariable("attribute_id") String attributeValue,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Participant>>(HttpStatus.UNAUTHORIZED);
		}

		return participantService.getParticipantByAttributeValue(attributeId, attributeValue);
	}

	/**
	 * Method for creating participant
	 * 
	 * @param participant
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Create new Participant", notes = "Create new participant instanse by participant first name, last name and email", tags = "Participant")
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> setParticipant(@RequestBody Participant participant, @RequestHeader String token) {

		// if (!authorizationService.checkAccess(token)) {
		//
		// return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		// }

		return participantService.setParticipant(participant);
	}

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update Participant By Id", notes = "Update participant email, first name and last name by participant id", tags = "Participant")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Object> updateParticipant(@PathVariable Integer id, @RequestBody Participant participant,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return participantService.updateParticipant(participant, id);
	}

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete Participant By Id", notes = "Delete participant instanse by participant id", tags = "Participant")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteParticipantById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return participantService.deleteParticipantById(id);
	}
}
