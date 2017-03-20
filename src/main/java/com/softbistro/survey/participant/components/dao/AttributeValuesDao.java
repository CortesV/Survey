package com.softbistro.survey.participant.components.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.participant.components.interfaces.IAttributeValues;
import com.softbistro.survey.response.Response;

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
			+ "(survey.attribute_values.attribute_id, survey.attribute_values.participant_id, survey.attribute_values.attribute_value) VALUES (?, ?, ?)";
	private final static String SQL_FOR_GETTING_ATTRIBUTE_VALUES_BY_ID = "SELECT * FROM survey.attribute_values WHERE survey.attribute_values.id = ? "
			+ "AND survey.attribute_values.delete != 1";
	private final static String SQL_FOR_UPDATING_ATTRIBUTE_VALUES_BY_ID = "UPDATE survey.attribute_values AS av SET av.attribute_value = ? WHERE av.id = ?";
	private final static String SQL_FOR_DELETING_ATTRIBUTE_VALUES_BY_ID = "UPDATE survey.attribute_values AS av SET av.status = 'DELETE' WHERE av.id = ?";
	private final static String SQL_FOR_GETTING_PARTICIPANT_ATTRIBUTES = "SELECT av.attribute_value FROM survey.attribute_values AS av "
			+ "LEFT JOIN survey.attributes AS a ON av.attribute_id=a.id LEFT JOIN survey.group AS g ON a.group_id=g.id "
			+ "LEFT JOIN survey.connect_group_participant AS c ON g.id=c.group_id AND c.participant_id=av.participant_id "
			+ "LEFT JOIN survey.participant AS p ON c.participant_id=p.id WHERE g.id= ? and p.id= ? AND p.delete != 1";

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	@Override
	public Response setAttributeValues(AttributeValues attributeValues) {
		try {
			jdbcTemplate.update(SQL_FOR_SETTING_ATTRIBUTE_VALUES, attributeValues.getAttributeId(),
					attributeValues.getParticipantId(), attributeValues.getValue());
			return new Response(null, HttpStatus.CREATED, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	@Override
	public Response getAttributeValuesById(Integer attributeValuesId) {
		try {
			return new Response(
					jdbcTemplate.queryForObject(SQL_FOR_GETTING_ATTRIBUTE_VALUES_BY_ID,
							new BeanPropertyRowMapper<>(AttributeValues.class), attributeValuesId),
					HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return Response
	 */
	@Override
	public Response updateAttributeValuesById(AttributeValues attributeValues) {
		try {
			jdbcTemplate.update(SQL_FOR_UPDATING_ATTRIBUTE_VALUES_BY_ID, attributeValues.getAttributeId(),
					attributeValues.getParticipantId(), attributeValues.getValue(), attributeValues.getId());
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return Response
	 */
	@Override
	public Response deleteAttributeValuesById(Integer attributeValuesId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_ATTRIBUTE_VALUES_BY_ID, attributeValuesId);
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for getting all attribute values of participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@Override
	public Response getParticipantAttributesInGroup(Integer groupId, Integer participantId) {

		try {
			return new Response(
					jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_ATTRIBUTES,
							new BeanPropertyRowMapper<>(AttributeValues.class), groupId, participantId),
					HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
