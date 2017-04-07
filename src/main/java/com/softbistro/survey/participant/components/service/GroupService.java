package com.softbistro.survey.participant.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.interfaces.IGroup;

/**
 * Service for group entity
 * 
 * @author af150416
 *
 */
@Service
public class GroupService {

	@Autowired
	private IGroup iGroup;

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public void setGroup(Group group) {

		iGroup.setGroup(group);
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public Group getGroupById(Integer groupId) {

		return iGroup.getGroupByid(groupId);
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	public List<Group> getGroupsByClient(Integer clientId) {

		return iGroup.getGroupsByClient(clientId);
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public void updateGroupById(Group group, Integer id) {

		iGroup.updateGroupById(group, id);
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public void deleteGroupById(Integer groupId) {

		iGroup.deleteGroupById(groupId);
	}
}
