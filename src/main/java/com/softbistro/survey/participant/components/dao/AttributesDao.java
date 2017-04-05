package com.softbistro.survey.participant.components.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.participant.components.interfaces.IAttributes;

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

	private final static String SQL_FOR_SETTING_ATTRIBUTES = "INSERT INTO attributes (attributes.group_id, attributes.attribute) VALUES (?, ?)";
	private final static String SQL_FOR_GETTING_ATTRIBUTES_BY_ID = "SELECT * FROM attributes AS at WHERE at.id = ? AND at.delete = 0";
	private final static String SQL_FOR_UPDATING__ATTRIBUTES_BY_ID = "UPDATE attributes AS at SET at.group_id = ?, at.attribute = ? WHERE at.id = ?";
	private final static String SQL_FOR_DELETING_ATTRIBUTES = "UPDATE attributes AS at SET at.delete = 1, WHERE at.id = ?";
	private final static String SQL_FOR_GETTING_ATTRIBUTES_BY_GROUP = "SELECT * FROM attributes WHERE attributes.group_id = ? AND attributes.delete = 0";

	/**
	 * Method for creating the attribute value
	 * 
	 * @param Attributes
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> setAttribute(Attributes attributes) {

		try {
			jdbcTemplate.update(SQL_FOR_SETTING_ATTRIBUTES, attributes.getGroupId(), attributes.getAttribute());
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Attributes> getAttributeById(Integer attributesId) {

		try {
			List<Attributes> list = jdbcTemplate.query(SQL_FOR_GETTING_ATTRIBUTES_BY_ID,
					new BeanPropertyRowMapper<>(Attributes.class), attributesId);

			return list.isEmpty() ? new ResponseEntity<Attributes>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<Attributes>(list.get(0), HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Attributes>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for updating attribute
	 * 
	 * @param Attributes
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> updateAttributes(Attributes attributes, Integer attributesId) {

		try {
			jdbcTemplate.update(SQL_FOR_UPDATING__ATTRIBUTES_BY_ID, attributes.getGroupId(), attributes.getAttribute(),
					attributesId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> deleteAttributes(Integer attributesId) {

		try {
			jdbcTemplate.update(SQL_FOR_DELETING_ATTRIBUTES, attributesId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to getting all attributes in group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<List<Attributes>> getAttributesByGroup(Integer groupId) {

		try {
			List<Attributes> list = jdbcTemplate.query(SQL_FOR_GETTING_ATTRIBUTES_BY_GROUP,
					new BeanPropertyRowMapper<>(Attributes.class), groupId);

			return list.isEmpty() ? new ResponseEntity<List<Attributes>>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<List<Attributes>>(list, HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<List<Attributes>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
