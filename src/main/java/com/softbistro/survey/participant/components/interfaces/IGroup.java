package com.softbistro.survey.participant.components.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.participant.components.entity.Group;

/**
 * Interface for Group entity
 * 
 * @author af150416
 *
 */
public interface IGroup {

	public ResponseEntity<Object> setGroup(Group group);

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public ResponseEntity<Group> getGroupByid(Integer groupId);

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Group>> getGroupsByClient(Integer clientId);

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> updateGroupById(Group group, Integer id);

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deleteGroupById(Integer groupId);
}
