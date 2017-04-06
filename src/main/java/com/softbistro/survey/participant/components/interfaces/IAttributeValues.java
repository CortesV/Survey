package com.softbistro.survey.participant.components.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.participant.components.entity.AttributeValues;

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
	public ResponseEntity<Object> setAttributeValues(AttributeValues attributeValues);

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	public ResponseEntity<AttributeValues> getAttributeValuesById(Integer attributeValuesId);

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues,
	 *            id
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> updateAttributeValuesById(AttributeValues attributeValues, Integer id);

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deleteAttributeValuesById(Integer attributeValuesId);

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<AttributeValues>> getParticipantAttributesInGroup(Integer groupId,
			Integer participantId);

}
