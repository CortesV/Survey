package com.softbistro.survey.participant.components.interfaces;

import java.util.List;

import com.softbistro.survey.participant.components.entity.Attributes;

/**
 * Interface for attributes value
 * 
 * @author af150416
 *
 */
public interface IAttributes {

	/**
	 * Method for creating the attribute value
	 * 
	 * @param Attributes
	 * @return ResponseEntity
	 */
	public void setAttribute(Attributes atributes);

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	public Attributes getAttributeById(Integer attributesId);

	/**
	 * Method for updating attribute
	 * 
	 * @param Attributes, attributesId
	 * @return ResponseEntity
	 */
	public void updateAttributes(Attributes attributes, Integer attributesId);

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	public void deleteAttributes(Integer attributesId);

	/**
	 * Method to getting all attributes in group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public List<Attributes> getAttributesByGroup(Integer groupId);
}
