package com.softbistro.survey.participant.components.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.participant.components.interfaces.IAttributes;
import com.softbistro.survey.response.Response;

/**
 * Service for attribute entity
 * 
 * @author af150416
 *
 */
@Service
public class AttributesService {

	@Autowired
	private IAttributes iAttributes;

	/**
	 * Method for creating the attribute value
	 * 
	 * @param Attributes
	 * @return Response
	 */
	public Response setAttribute(Attributes attributes) {
		return iAttributes.setAttribute(attributes);
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	public Response getAttributeById(Integer attributesId) {
		return iAttributes.getAttributeById(attributesId);
	}

	/**
	 * Method for updating attribute
	 * 
	 * @param Attributes
	 * @return Response
	 */
	public Response updateAttributes(Attributes attributes) {
		return iAttributes.updateAttributes(attributes);
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	public Response deleteAttributesById(Integer attributesId) {
		return iAttributes.deleteAttributes(attributesId);
	}

	/**
	 * Method to getting all attributes by group
	 * 
	 * @param groupId
	 * @Response
	 */
	public Response getAttributesByGroupId(Integer groupId) {
		return iAttributes.getAttributesByGroup(groupId);
	}
}
