package com.softbistro.survey.participant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.component.entity.Attributes;
import com.softbistro.survey.participant.component.interfaces.IAttributes;

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
	public Integer setAttribute(Attributes attributes) {

		return iAttributes.setAttribute(attributes);
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	public Attributes getAttributeById(Integer attributesId) {

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
	public void updateAttributes(Attributes attributes, Integer attributesId) {

		iAttributes.updateAttributes(attributes, attributesId);
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	public void deleteAttributesById(Integer attributesId) {

		iAttributes.deleteAttributes(attributesId);
	}

	/**
	 * Method to getting all attributes by group
	 * 
	 * @param groupId
	 * @ResponseEntity
	 */
	public List<Attributes> getAttributesByGroupId(Integer groupId) {

		return iAttributes.getAttributesByGroup(groupId);
	}
}
