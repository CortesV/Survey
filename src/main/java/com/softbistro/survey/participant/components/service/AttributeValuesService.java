package com.softbistro.survey.participant.components.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.participant.components.interfaces.IAttributeValues;
import com.softbistro.survey.response.Response;

/**
 * Service for attribute Values entity
 * 
 * @author af150416
 *
 */
@Service
public class AttributeValuesService {

	@Autowired
	IAttributeValues iAttributeValues;

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	public Response setAttributeValues(AttributeValues attributeValues) {
		return iAttributeValues.setAttributeValues(attributeValues);
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	public Response getAttributeValuesById(Integer attributeValuesId) {
		return iAttributeValues.getAttributeValuesById(attributeValuesId);
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	public Response updateAttributeValuesById(AttributeValues attributeValues) {
		return iAttributeValues.updateAttributeValuesById(attributeValues);
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	public Response deleteAttributeValuesById(Integer attributeValuesId) {
		return iAttributeValues.deleteAttributeValuesById(attributeValuesId);
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	public Response getParticipantAttributesInGroup(Integer groupId, Integer participantId) {
		return iAttributeValues.getParticipantAttributesInGroup(groupId, participantId);
	}
}
