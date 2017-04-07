package com.softbistro.survey.participant.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.participant.components.interfaces.IAttributeValues;

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
	 * @return ResponseEntity
	 */
	public void setAttributeValues(AttributeValues attributeValues) {

		iAttributeValues.setAttributeValues(attributeValues);
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	public AttributeValues getAttributeValuesById(Integer attributeValuesId) {

		return iAttributeValues.getAttributeValuesById(attributeValuesId);
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues,
	 *            id
	 * @return ResponseEntity
	 */
	public void updateAttributeValuesById(AttributeValues attributeValues, Integer id) {

		iAttributeValues.updateAttributeValuesById(attributeValues, id);
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	public void deleteAttributeValuesById(Integer attributeValuesId) {

		iAttributeValues.deleteAttributeValuesById(attributeValuesId);
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	public List<AttributeValues> getParticipantAttributesInGroup(Integer groupId, Integer participantId) {

		return iAttributeValues.getParticipantAttributesInGroup(groupId, participantId);
	}
}
