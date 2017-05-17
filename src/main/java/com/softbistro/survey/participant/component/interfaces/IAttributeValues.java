package com.softbistro.survey.participant.component.interfaces;

import java.util.List;

import com.softbistro.survey.participant.component.entity.AttributeValues;

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
	 * @return ResponseEntity
	 */
	public Integer setAttributeValues(AttributeValues attributeValues);

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	public AttributeValues getAttributeValuesById(Integer attributeValuesId);

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues,
	 *            id
	 * @return ResponseEntity
	 */
	public void updateAttributeValuesById(AttributeValues attributeValues, Integer id);

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	public void deleteAttributeValuesById(Integer attributeValuesId);

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public List<AttributeValues> getParticipantAttributesInGroup(Integer groupId, Integer participantId);

}
