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

	private final static String SQL_FOR_SETTING_GROUP = "INSERT INTO survey.group (survey.group.client_id, survey.group.group_name) "
			+ "VALUES (?, ?)";
	private final static String SQL_FOR_GETTING_GROUP_BY_ID = "SELECT g.id, g.client_id, g.group_name, g.created_date, g.modified_date "
			+ "FROM survey.group AS g WHERE g.id = ? AND g.delete = 0";
	private final static String SQL_FOR_GETTING_GROUP_BY_CLIENT = "SELECT g.id, g.client_id, g.group_name, g.created_date, g.modified_date "
			+ "FROM survey.group AS g WHERE g.client_id = ? AND g.delete = 0";
	private final static String SQL_FOR_UPDATING_GROUP_BY_ID = "UPDATE survey.group AS g SET g.group_name = ? WHERE g.id = ?";
	private final static String SQL_FOR_DELETING_GROUP_BY_ID = "UPDATE survey.group AS g LEFT JOIN survey.connect_group_participant AS cp ON cp.group_id=g.id "
			+ "LEFT JOIN survey.connect_group_survey AS cs ON cs.group_id=g.id LEFT JOIN survey.attributes AS at ON at.group_id=g.id "
			+ "LEFT JOIN survey.attribute_values AS av ON av.attribute_id=at.id "
			+ "SET g.delete = 1, cp.delete = 1, cs.delete = 1, at.delete = 1, av.delete = 1 WHERE g.id = ?";

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
