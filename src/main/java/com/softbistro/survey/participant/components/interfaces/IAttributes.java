package com.softbistro.survey.participant.components.interfaces;

import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.response.Response;

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
	 * @return Response
	 */
	public Response setAttribute(Attributes atributes);

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	public Response getAttributeById(Integer attributesId);

	/**
	 * Method for updating attribute
	 * 
	 * @param Attributes
	 * @return Response
	 */
	public Response updateAttributes(Attributes attributes);

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	public Response deleteAttributes(Integer attributesId);

	/**
	 * Method to getting all attributes in group
	 * 
	 * @param groupId
	 * @return Response
	 */
	public Response getAttributesByGroup(Integer groupId);
}
