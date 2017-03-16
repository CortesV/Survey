package com.softbistro.survey.participant.components.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.participant.components.interfaces.IAttributes;
import com.softbistro.survey.response.Response;

/**
 * Data access object for attributes entity
 * 
 * @author af150416
 *
 */
@Repository
public class AttributesDao implements IAttributes {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String SQL_FOR_SETTING_ATTRIBUTES = "INSERT INTO survey.attributes (survey.attributes.group_id, survey.attributes.attribute) VALUES (?, ?)";
	private final String SQL_FOR_GETTING_ATTRIBUTES_BY_ID = "SELECT * FROM survey.attributes AS at WHERE at.id = ?";
	private final String SQL_FOR_UPDATING__ATTRIBUTES_BY_ID = "UPDATE survey.attributes AS at SET at.group_id = ?, at.attribute = ? WHERE at.id=?";
	private final String SQL_FOR_DELETING_ATTRIBUTES = "UPDATE survey.attributes AS at LEFT JOIN survey.attribute_values AS av ON  av.attribute_id=at.id SET at.status = 'DELETE', av.status = 'DELETE' WHERE at.id = ? ";
	private final String SQL_FOR_GETTING_ATTRIBUTES_BY_GROUP = "SELECT * FROM survey.attributes WHERE survey.attributes.group_id = ?";

	/**
	 * Method for creating the attribute value
	 * 
	 * @param Attributes
	 * @return Response
	 */
	@Override
	public Response setAttribute(Attributes attributes) {

		try {
			jdbcTemplate.update(SQL_FOR_SETTING_ATTRIBUTES, attributes.getGroupId(), attributes.getAttribute());
			return new Response(null, HttpStatus.CREATED, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	@Override
	public Response getAttributeById(Integer attributesId) {

		try {
			return new Response(jdbcTemplate.queryForObject(SQL_FOR_GETTING_ATTRIBUTES_BY_ID,
					new BeanPropertyRowMapper<>(Attributes.class), attributesId), HttpStatus.OK, null);

		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for updating attribute
	 * 
	 * @param Attributes
	 * @return Response
	 */
	@Override
	public Response updateAttributes(Attributes attributes) {

		try {
			jdbcTemplate.update(SQL_FOR_UPDATING__ATTRIBUTES_BY_ID, attributes.getGroupId(), attributes.getAttribute(),
					attributes.getId());
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return Response
	 */
	@Override
	public Response deleteAttributes(Integer attributesId) {

		try {
			jdbcTemplate.update(SQL_FOR_DELETING_ATTRIBUTES, attributesId);
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to getting all attributes in group
	 * 
	 * @param groupId
	 * @return Response
	 */
	@Override
	public Response getAttributesByGroup(Integer groupId) {

		try {
			return new Response(jdbcTemplate.query(SQL_FOR_GETTING_ATTRIBUTES_BY_GROUP,
					new BeanPropertyRowMapper<>(Attributes.class), groupId), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
