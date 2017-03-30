package com.softbistro.survey.participant.components.interfaces;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.response.Response;

/**
 * Interface for Group entity
 * 
 * @author af150416
 *
 */
public interface IGroup {

	public Response setGroup(Group group);

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return Response
	 */
	public Response getGroupByid(Integer groupId);

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return Response
	 */
	public Response getGroupsByClient(Integer clientId);

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return Response
	 */
	public Response updateGroupById(Group group);

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return Response
	 */
	public Response deleteGroupById(Integer groupId);
}
