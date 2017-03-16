package com.softbistro.survey.participant.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.participant.components.service.AttributesService;
import com.softbistro.survey.response.Response;

/**
 * Controller
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/attribute")
public class AttributesController {

	@Autowired
	AttributesService attributesService;

	/**
	 * Method for creating the attribute value
	 * 
	 * @param attributes
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setAttribute(@RequestBody Attributes attributes) {
		return attributesService.setAttribute(attributes);
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	@RequestMapping(value = "/{attributesId}", method = RequestMethod.GET)
	public Response getAttributesById(@PathVariable Integer attributesId) {
		return attributesService.getAttributeById(attributesId);
	}

	/**
	 * Method to getting all attributes on group
	 * 
	 * @param groupId
	 * @return Response
	 */
	@RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
	public Response getAttributesByGroupId(@PathVariable Integer groupId) {
		return attributesService.getAttributesByGroupId(groupId);
	}

	/**
	 * Method for updating attribute
	 * 
	 * @param attributes
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateAttributesById(@RequestBody Attributes attributes) {
		return attributesService.updateAttributes(attributes);
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	@RequestMapping(value = "/{attributesId}", method = RequestMethod.DELETE)
	public Response deleteAttributesById(@PathVariable Integer attributesId) {
		return attributesService.deleteAttributesById(attributesId);
	}
}