package com.softbistro.survey.participant.components.controller;

import java.util.List;

import org.apache.log4j.Logger;
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

	private static final Logger LOGGER = Logger.getLogger(GroupController.class);

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
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> setGroup(@RequestBody Group group, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(groupService.setGroup(group), HttpStatus.CREATED);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Group By Id", notes = "Get group instanse by group id", tags = "Participant Group")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Group> getGroupById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(groupService.getGroupById(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to get all client groups
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get Group By Client", notes = "Get group instanse by group id", tags = "Participant Group")
	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Group>> getGroupByClientId(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(groupService.getGroupsByClient(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update Group Name By Id", notes = "Update group instanse by group name and group id", tags = "Participant Group")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Object> updateGroup(@PathVariable Integer id, @RequestBody Group group,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			groupService.updateGroupById(group, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete Group By Id", notes = "Delete group instanse by group id", tags = "Participant Group")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteGroupById(@PathVariable Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			groupService.deleteGroupById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
