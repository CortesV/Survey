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
import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.participant.components.service.AttributesService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for Participant entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/attribute")
public class AttributesController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private AttributesService attributesService;

	/**
	 * Method for creating the attribute value
	 * 
	 * @param attributes
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Create new Attribute", notes = "Create new attribute instanse by attribute naming and group id", tags = "Attribute")
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> setAttribute(@RequestBody Attributes attributes, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return attributesService.setAttribute(attributes);
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attribute By Id", notes = "Get attribute instanse by attribute id", tags = "Attribute")
	@RequestMapping(value = "/{attributes_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Attributes> getAttributesById(@PathVariable("attributes_id") Integer attributesId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Attributes>(HttpStatus.UNAUTHORIZED);
		}

		return attributesService.getAttributeById(attributesId);
	}

	/**
	 * Method to getting all attributes on group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Attributes By Group", notes = "Get all attributes by group id", tags = "Attribute")
	@RequestMapping(value = "/group/{group_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Attributes>> getAttributesByGroupId(@PathVariable("group_id") Integer groupId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Attributes>>(HttpStatus.UNAUTHORIZED);
		}

		return attributesService.getAttributesByGroupId(groupId);
	}

	/**
	 * Method for updating attribute
	 * 
	 * @param attributes
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update Attribute", notes = "Update attribute instanse by attribute naming, group id and attribute id", tags = "Attribute")
	@RequestMapping(value = "/{attributes_id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Object> updateAttributesById(@PathVariable("attributes_id") Integer attributesId,
			@RequestBody Attributes attributes, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return attributesService.updateAttributes(attributes, attributesId);
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete Attribute", notes = "Delete attribute instanse by attribute id", tags = "Attribute")
	@RequestMapping(value = "/{attributes_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteAttributesById(@PathVariable("attributes_id") Integer attributesId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return attributesService.deleteAttributesById(attributesId);
	}
}
