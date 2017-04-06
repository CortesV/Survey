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
import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.participant.components.service.AttributeValuesService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for attribute values entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/attribute_value")
public class AttributeValuesController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private AttributeValuesService attributeValuesService;

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Create new Attribute Value", notes = "Create new attribute value instanse by attribute id, participant id, value", tags = "Attribute Value")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> setAttributeValues(@RequestBody AttributeValues attributeValues,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return attributeValuesService.setAttributeValues(attributeValues);
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attribute Value By Id", notes = "Get attribute value instanse by attribute value id", tags = "Attribute Value")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AttributeValues> getAttributevaluesById(@PathVariable Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<AttributeValues>(HttpStatus.UNAUTHORIZED);
		}

		return attributeValuesService.getAttributeValuesById(id);
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update Attribute Value By Id", notes = "Get attribute value instanse by attribute value id", tags = "Attribute Value")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> updateAttributeValuesById(@PathVariable Integer id,
			@RequestBody AttributeValues attributeValues, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return attributeValuesService.updateAttributeValuesById(attributeValues, id);
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attribute Value By Id", notes = "Get attribute value instanse by attribute value id", tags = "Attribute Value")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAttributeValuesById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		return attributeValuesService.deleteAttributeValuesById(id);
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attribute Values By Participant in Group", notes = "Get all attribute values of participant in group by group id and participant id", tags = "Attribute Value")
	@RequestMapping(value = "/{group_id}/{participant_id}", method = RequestMethod.GET)
	public ResponseEntity<List<AttributeValues>> getParticipantAttributeValuesInGroup(
			@PathVariable("group_id") Integer groupId, @PathVariable("participant_id") Integer participantId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<AttributeValues>>(HttpStatus.UNAUTHORIZED);
		}

		return attributeValuesService.getParticipantAttributesInGroup(groupId, participantId);
	}
}
