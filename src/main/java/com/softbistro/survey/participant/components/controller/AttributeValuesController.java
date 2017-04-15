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

	private static final Logger LOGGER = Logger.getLogger(AttributeValuesController.class);

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
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> setAttributeValues(@RequestBody AttributeValues attributeValues,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(attributeValuesService.setAttributeValues(attributeValues), HttpStatus.CREATED);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attribute Value By Id", notes = "Get attribute value instanse by attribute value id", tags = "Attribute Value")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<AttributeValues> getAttributevaluesById(@PathVariable Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(attributeValuesService.getAttributeValuesById(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update Attribute Value By Id", notes = "Update attribute value instanse by attribute value id", tags = "Attribute Value")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Object> updateAttributeValuesById(@PathVariable Integer id,
			@RequestBody AttributeValues attributeValues, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			attributeValuesService.updateAttributeValuesById(attributeValues, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attribute Value By Id", notes = "Get attribute value instanse by attribute value id", tags = "Attribute Value")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteAttributeValuesById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		try {

			attributeValuesService.deleteAttributeValuesById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attribute Values By Participant in Group", notes = "Get all attribute values of participant in group by group id and participant id", tags = "Attribute Value")
	@RequestMapping(value = "/{group_id}/{participant_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<AttributeValues>> getParticipantAttributeValuesInGroup(
			@PathVariable("group_id") Integer groupId, @PathVariable("participant_id") Integer participantId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(attributeValuesService.getParticipantAttributesInGroup(groupId, participantId),
					HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
