package com.softbistro.survey.participant.components.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.interfaces.IGroup;
import com.softbistro.survey.response.Response;

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
			+ "FROM survey.group AS g WHERE g.id = ? AND g.delete != 1";
	private final static String SQL_FOR_GETTING_GROUP_BY_CLIENT = "SELECT g.id, g.client_id, g.group_name, g.created_date, g.modified_date "
			+ "FROM survey.group AS g WHERE g.client_id = ? AND g.delete != 1";
	private final static String SQL_FOR_UPDATING_GROUP_BY_ID = "UPDATE survey.group AS g SET g.group_name = ? WHERE g.id = ?";
	private final static String SQL_FOR_DELETING_GROUP_BY_ID = "UPDATE survey.group AS g LEFT JOIN survey.connect_group_participant AS cp ON cp.group_id=g.id "
			+ "LEFT JOIN survey.connect_group_survey AS cs ON cs.group_id=g.id LEFT JOIN survey.attributes AS at ON at.group_id=g.id "
			+ "LEFT JOIN survey.attribute_values AS av ON av.attribute_id=at.id "
			+ "SET g.status = 'DELETE', cp.status = 'DELETE', cs.status = 'DELETE', at.status = 'DELETE', av.status = 'DELETE'WHERE g.id = ?";

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return Response
	 */
	@Override
	public Response setGroup(Group group) {
		try {
			jdbcTemplate.update(SQL_FOR_SETTING_GROUP, group.getClientId(), group.getGroupName());
			return new Response(null, HttpStatus.CREATED, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return Response
	 */
	@Override
	public Response getGroupByid(Integer groupId) {
		try {
			return new Response(jdbcTemplate.queryForObject(SQL_FOR_GETTING_GROUP_BY_ID,
					new BeanPropertyRowMapper<>(Group.class), groupId), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return Response
	 */
	@Override
	public Response getGroupsByClient(Integer clientId) {
		try {
			return new Response(jdbcTemplate.query(SQL_FOR_GETTING_GROUP_BY_CLIENT,
					new BeanPropertyRowMapper<>(Group.class), clientId), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return Response
	 */
	@Override
	public Response updateGroupById(Group group) {
		try {
			jdbcTemplate.update(SQL_FOR_UPDATING_GROUP_BY_ID, group.getGroupName(), group.getId());
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return Response
	 */
	@Override
	public Response deleteGroupById(Integer groupId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_GROUP_BY_ID, groupId);
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
