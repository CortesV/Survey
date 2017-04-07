package com.softbistro.survey.participant.components.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> setGroup(Group group) {
		return iGroup.setGroup(group);
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Group> getGroupById(Integer groupId) {
		return iGroup.getGroupByid(groupId);
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Group>> getGroupsByClient(Integer clientId) {
		return iGroup.getGroupsByClient(clientId);
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> updateGroupById(Group group, Integer id) {
		return iGroup.updateGroupById(group, id);
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deleteGroupById(Integer groupId) {
		return iGroup.deleteGroupById(groupId);
	}
}
