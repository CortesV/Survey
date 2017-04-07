package com.softbistro.survey.participant.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.participant.components.interfaces.IAttributes;

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
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> setAttribute(Attributes attributes) {
		return iAttributes.setAttribute(attributes);
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Attributes> getAttributeById(Integer attributesId) {
		return iAttributes.getAttributeById(attributesId);
	}

	/**
	 * Method for updating attribute
	 * 
	 * @param attributesId
	 * 
	 * @param Attributes
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> updateAttributes(Attributes attributes, Integer attributesId) {
		return iAttributes.updateAttributes(attributes, attributesId);
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deleteAttributesById(Integer attributesId) {
		return iAttributes.deleteAttributes(attributesId);
	}

	/**
	 * Method to getting all attributes by group
	 * 
	 * @param groupId
	 * @ResponseEntity
	 */
	public ResponseEntity<List<Attributes>> getAttributesByGroupId(Integer groupId) {
		return iAttributes.getAttributesByGroup(groupId);
	}
}
