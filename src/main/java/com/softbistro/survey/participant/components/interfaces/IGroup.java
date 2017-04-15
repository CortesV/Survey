package com.softbistro.survey.participant.components.interfaces;

import java.util.List;

import com.softbistro.survey.participant.components.entity.Group;

/**
 * Interface for Group entity
 * 
 * @author af150416
 *
 */
public interface IGroup {

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public Integer setGroup(Group group);

	/**
	 * Method to get group by id
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public Group getGroupByid(Integer groupId);

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	public List<Group> getGroupsByClient(Integer clientId);

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	public void updateGroupById(Group group, Integer id);

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	public void deleteGroupById(Integer groupId);
}
