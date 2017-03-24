package com.softbistro.survey.participant.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
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
	
	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private GroupService groupService;

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setGroup(@RequestBody Group group, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return groupService.setGroup(group);
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response getGroupById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return groupService.getGroupById(id);
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return Response
	 */
	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public Response getGroupByClientId(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return groupService.getGroupsByClient(id);
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateGroup(@RequestBody Group group, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return groupService.updateGroupById(group);
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteGroupById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}
		
		return groupService.deleteGroupById(id);
	}
}
