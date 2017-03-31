package com.softbistro.survey.participant.components.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.participant.components.interfaces.IAttributeValues;

/**
 * Data access object for attribute values entity
 * 
 * @author af150416
 *
 */
@Repository
public class AttributeValuesDao implements IAttributeValues {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final static String SQL_FOR_SETTING_ATTRIBUTE_VALUES = "INSERT INTO survey.attribute_values "
			+ "(survey.attribute_values.attribute_id, survey.attribute_values.participant_id, survey.attribute_values.attribute_value)"
			+ " VALUES (?, ?, ?)";
	private final static String SQL_FOR_GETTING_ATTRIBUTE_VALUES_BY_ID = "SELECT * FROM survey.attribute_values "
			+ "WHERE survey.attribute_values.id = ? AND survey.attribute_values.delete = 0";
	private final static String SQL_FOR_UPDATING_ATTRIBUTE_VALUES_BY_ID = "UPDATE survey.attribute_values AS av "
			+ "SET av.attribute_value = ? WHERE av.id = ?";
	private final static String SQL_FOR_DELETING_ATTRIBUTE_VALUES_BY_ID = "UPDATE survey.attribute_values AS av"
			+ " SET av.delete = 0 WHERE av.id = ?";
	private final static String SQL_FOR_GETTING_PARTICIPANT_ATTRIBUTES = "SELECT av.attribute_value FROM survey.attribute_values AS av "
			+ "LEFT JOIN survey.attributes AS a ON av.attribute_id=a.id LEFT JOIN survey.group AS g ON a.group_id=g.id "
			+ "LEFT JOIN survey.connect_group_participant AS c ON g.id=c.group_id AND c.participant_id=av.participant_id "
			+ "LEFT JOIN survey.participant AS p ON c.participant_id=p.id WHERE g.id= ? and p.id= ? AND p.delete = 0";

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> setAttributeValues(AttributeValues attributeValues) {
		try {
			jdbcTemplate.update(SQL_FOR_SETTING_ATTRIBUTE_VALUES, attributeValues.getAttributeId(),
					attributeValues.getParticipantId(), attributeValues.getValue());
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<AttributeValues> getAttributeValuesById(Integer attributeValuesId) {
		try {
			List<AttributeValues> list = (List<AttributeValues>) jdbcTemplate.query(
					SQL_FOR_GETTING_ATTRIBUTE_VALUES_BY_ID, new BeanPropertyRowMapper<>(AttributeValues.class),
					attributeValuesId);

			return list.isEmpty() ? new ResponseEntity<AttributeValues>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<AttributeValues>(list.get(0), HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<AttributeValues>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> updateAttributeValuesById(AttributeValues attributeValues, Integer id) {
		try {
			jdbcTemplate.update(SQL_FOR_UPDATING_ATTRIBUTE_VALUES_BY_ID, attributeValues.getAttributeId(),
					attributeValues.getParticipantId(), attributeValues.getValue(), id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> deleteAttributeValuesById(Integer attributeValuesId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_ATTRIBUTE_VALUES_BY_ID, attributeValuesId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<List<AttributeValues>> getParticipantAttributesInGroup(Integer groupId,
			Integer participantId) {
		try {
			List<AttributeValues> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_ATTRIBUTES,
					new BeanPropertyRowMapper<>(AttributeValues.class), groupId, participantId);

			return list.isEmpty() ? new ResponseEntity<List<AttributeValues>>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<List<AttributeValues>>(list, HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<List<AttributeValues>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
