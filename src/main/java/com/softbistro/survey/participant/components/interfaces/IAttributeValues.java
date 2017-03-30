package com.softbistro.survey.participant.components.interfaces;

import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.response.Response;

/**
 * Interface for attribute values entity
 * 
 * @author af150416
 *
 */
public interface IAttributeValues {

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	public Response setAttributeValues(AttributeValues attributeValues);

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	public Response getAttributeValuesById(Integer attributeValuesId);

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	public Response updateAttributeValuesById(AttributeValues attributeValues);

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	public Response deleteAttributeValuesById(Integer attributeValuesId);

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	public Response getParticipantAttributesInGroup(Integer groupId, Integer participantId);

}
