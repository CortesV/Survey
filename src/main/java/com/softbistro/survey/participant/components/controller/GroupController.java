package com.softbistro.survey.participant.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.service.GroupService;
import com.softbistro.survey.response.Response;

/**
 * Controller for group entity
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/group")
public class GroupController {

	@Autowired
	GroupService groupService;

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setGroup(@RequestBody Group group) {
		return groupService.setGroup(group);
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return Response
	 */
	@RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
	public Response getGroupById(@PathVariable Integer groupId) {
		return groupService.getGroupById(groupId);
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return Response
	 */
	@RequestMapping(value = "/client/{clientId}", method = RequestMethod.GET)
	public Response getGroupByClientId(@PathVariable Integer clientId) {
		return (Response) groupService.getGroupsByClient(clientId);
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateGroup(@RequestBody Group group) {
		return groupService.updateGroupById(group);
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return Response
	 */
	@RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
	public Response deleteGroupById(@PathVariable Integer groupId) {
		return groupService.deleteGroupById(groupId);
	}
}
