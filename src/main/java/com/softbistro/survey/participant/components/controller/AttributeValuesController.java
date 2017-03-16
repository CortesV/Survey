package com.softbistro.survey.participant.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/rest/survey/v1/attributeValue")
public class AttributeValuesController {

	@Autowired
	AttributeValuesService attributeValuesService;

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setAttributeValues(@RequestBody AttributeValues attributeValues) {
		return attributeValuesService.setAttributeValues(attributeValues);
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	@RequestMapping(value = "/{attributeValuesId}", method = RequestMethod.GET)
	public Response getAttributevaluesById(@PathVariable Integer attributeValuesId) {
		return attributeValuesService.getAttributeValuesById(attributeValuesId);
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateAttributeValuesById(@RequestBody AttributeValues attributeValues) {
		return attributeValuesService.updateAttributeValuesById(attributeValues);
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	@RequestMapping(value = "/{attributeValuesId}", method = RequestMethod.DELETE)
	public Response deleteAttributeValuesById(@PathVariable Integer attributeValuesId) {
		return attributeValuesService.deleteAttributeValuesById(attributeValuesId);
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@RequestMapping(value = "/{groupId}/{participantId}", method = RequestMethod.GET)
	public Response getParticipantAttributeValuesInGroup(@PathVariable Integer groupId, Integer participantId) {
		return attributeValuesService.getParticipantAttributesInGroup(groupId, participantId);
	}
}
