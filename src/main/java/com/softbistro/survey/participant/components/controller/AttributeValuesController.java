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
import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.participant.components.service.AttributeValuesService;
import com.softbistro.survey.response.Response;

/**
 * Controller for attribute values entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/attribute_value")
public class AttributeValuesController {

	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private AttributeValuesService attributeValuesService;

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setAttributeValues(@RequestBody AttributeValues attributeValues, @RequestHeader String token) {
		
		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return attributeValuesService.setAttributeValues(attributeValues);
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response getAttributevaluesById(@PathVariable Integer id, @RequestHeader String token) {
		
		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return attributeValuesService.getAttributeValuesById(id);
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateAttributeValuesById(@RequestBody AttributeValues attributeValues, @RequestHeader String token) {
		
		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return attributeValuesService.updateAttributeValuesById(attributeValues);
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteAttributeValuesById(@PathVariable Integer id, @RequestHeader String token) {
		
		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return attributeValuesService.deleteAttributeValuesById(id);
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{group_id}/{participant_id}", method = RequestMethod.GET)
	public Response getParticipantAttributeValuesInGroup(@PathVariable("group_id") Integer groupId,
			@PathVariable("participant_id") Integer participantId, @RequestHeader String token) {
		
		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return attributeValuesService.getParticipantAttributesInGroup(groupId, participantId);
	}
}
