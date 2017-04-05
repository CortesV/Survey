package com.softbistro.survey.participant.components.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.interfaces.IGroup;

/**
 * Data access object for group entity
 * 
 * @author af150416
 *
 */
@Repository
public class GroupDao implements IGroup {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final static String SQL_FOR_SETTING_GROUP = "INSERT INTO group (group.client_id, group.group_name) VALUES (?, ?)";
	private final static String SQL_FOR_GETTING_GROUP_BY_ID = "SELECT * FROM group AS g WHERE g.id = ? AND g.delete = 0";
	private final static String SQL_FOR_GETTING_GROUP_BY_CLIENT = "SELECT * FROM survey.group AS g WHERE g.client_id = ? AND g.delete = 0";
	private final static String SQL_FOR_UPDATING_GROUP_BY_ID = "UPDATE group AS g SET g.group_name = ? WHERE g.id = ?";
	private final static String SQL_FOR_DELETING_GROUP_BY_ID = "UPDATE survey.group AS g SET g.delete = 1 WHERE g.id = ?";

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> setGroup(Group group) {
		try {
			jdbcTemplate.update(SQL_FOR_SETTING_GROUP, group.getClientId(), group.getGroupName());
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Group> getGroupByid(Integer groupId) {
		try {
			List<Group> list = jdbcTemplate.query(SQL_FOR_GETTING_GROUP_BY_ID, new BeanPropertyRowMapper<>(Group.class),
					groupId);

			return list.isEmpty() ? new ResponseEntity<Group>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<Group>(list.get(0), HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Group>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<List<Group>> getGroupsByClient(Integer clientId) {
		try {
			List<Group> list = jdbcTemplate.query(SQL_FOR_GETTING_GROUP_BY_CLIENT,
					new BeanPropertyRowMapper<>(Group.class), clientId);

			return list.isEmpty() ? new ResponseEntity<List<Group>>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<List<Group>>(list, HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<List<Group>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> updateGroupById(Group group, Integer id) {
		try {
			jdbcTemplate.update(SQL_FOR_UPDATING_GROUP_BY_ID, group.getGroupName(), id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> deleteGroupById(Integer groupId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_GROUP_BY_ID, groupId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
