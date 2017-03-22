package com.softbistro.survey.participant.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@Autowired
	private ParticipantService participantService;

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response getParticipantById(@PathVariable Integer id) {
		return participantService.getParticipantById(id);
	}

	/**
	 * Method to getting participant from db by email and client Id
	 * 
	 * @param email,
	 *            clientid
	 * @return Response
	 */
	@RequestMapping(value = "email/{email}/{clientId}", method = RequestMethod.GET)
	public Response getParticipantByEmailAndClientId(@PathVariable String email, Integer clientid) {
		return participantService.getParticipantByEmailAndClientId(email, clientid);
	}

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attribute value
	 * @return Response
	 */
	@RequestMapping(value = "attribute/{attributeId}/{attributeValue}", method = RequestMethod.GET)
	public Response getParticipantByAttributeValue(@PathVariable Integer attributeId, String attributeValue) {
		return participantService.getParticipantByAttributeValue(attributeId, attributeValue);
	}

	/**
	 * Method for creating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setParticipant(@RequestBody Participant participant) {
		return participantService.setParticipant(participant);
	}

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateParticipant(@RequestBody Participant participant) {
		return participantService.updateParticipant(participant);
	}

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteParticipantById(@PathVariable Integer id) {
		return participantService.deleteParticipantById(id);
	}
}
