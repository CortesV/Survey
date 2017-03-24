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
import com.softbistro.survey.participant.components.service.ParticipantService;
import com.softbistro.survey.response.Response;

/**
 * Controller for participant entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/participant")
public class ParticipantController {
	
	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";

	@Autowired
	private AuthorizationService authorizationService;


	@Autowired
	private ParticipantService participantService;

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response getParticipantById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantService.getParticipantById(id);
	}

	/**
	 * Method to getting participant from db by email and client Id
	 * 
	 * @param email,
	 *            clientid
	 * @return Response
	 */
	@RequestMapping(value = "email/{email}/{client_id}", method = RequestMethod.GET)
	public Response getParticipantByEmailAndClientId(@PathVariable("email") String email,
			@PathVariable("client_id") Integer clientid, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantService.getParticipantByEmailAndClientId(email, clientid);
	}

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attribute value
	 * @return Response
	 */
	@RequestMapping(value = "attribute/{attribute_id}/{attribute_value}", method = RequestMethod.GET)
	public Response getParticipantByAttributeValue(@PathVariable("attribute_id") Integer attributeId,
			@PathVariable("attribute_id") String attributeValue, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantService.getParticipantByAttributeValue(attributeId, attributeValue);
	}

	/**
	 * Method for creating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setParticipant(@RequestBody Participant participant, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantService.setParticipant(participant);
	}

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateParticipant(@RequestBody Participant participant, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantService.updateParticipant(participant);
	}

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteParticipantById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return participantService.deleteParticipantById(id);
	}
}
