package com.softbistro.survey.participant.components.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.service.GroupService;

import io.swagger.annotations.ApiOperation;

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
	private AuthorizationService authorizationService;

	@Autowired
	private GroupService groupService;

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Create new Group", notes = "Create new group instanse by client id and group name", tags = "Participant Group")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> setGroup(@RequestBody Group group, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return groupService.setGroup(group);
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Group By Id", notes = "Get group instanse by group id", tags = "Participant Group")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Group> getGroupById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Group>(HttpStatus.UNAUTHORIZED);
		}

		return groupService.getGroupById(id);
	}

	/**
	 * Method to get all client groups
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Group By Client", notes = "Get group instanse by group id", tags = "Participant Group")
	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Group>> getGroupByClientId(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Group>>(HttpStatus.UNAUTHORIZED);
		}

		return groupService.getGroupsByClient(id);
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update Group By Id", notes = "Update group instanse by group name and group id", tags = "Participant Group")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> updateGroup(@PathVariable Integer id, @RequestBody Group group,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return groupService.updateGroupById(group, id);
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete Group By Id", notes = "Delete group instanse by group id", tags = "Participant Group")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteGroupById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return groupService.deleteGroupById(id);
	}
}
