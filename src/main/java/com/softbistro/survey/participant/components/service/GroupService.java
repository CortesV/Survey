package com.softbistro.survey.participant.components.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.interfaces.IGroup;
import com.softbistro.survey.response.Response;

/**
 * Service for group entity
 * 
 * @author af150416
 *
 */
@Service
public class GroupService {

	@Autowired
	IGroup iGroup;

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return Response
	 */
	public Response setGroup(Group group) {
		return iGroup.setGroup(group);
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return Response
	 */
	public Response getGroupById(Integer groupId) {
		return iGroup.getGroupByid(groupId);
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return Response
	 */
	public Response getGroupsByClient(Integer clientId) {
		return iGroup.getGroupsByClient(clientId);
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return Response
	 */
	public Response updateGroupById(Group group) {
		return iGroup.updateGroupById(group);
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return Response
	 */
	public Response deleteGroupById(Integer groupId) {
		return iGroup.deleteGroupById(groupId);
	}
}
