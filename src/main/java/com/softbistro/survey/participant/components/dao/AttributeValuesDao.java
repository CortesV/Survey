package com.softbistro.survey.participant.components.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	private static final Logger LOGGER = Logger.getLogger(AttributeValuesDao.class);

	private static final String SQL_FOR_SETTING_ATTRIBUTE_VALUES = "INSERT INTO attribute_values "
			+ "(attribute_values.attribute_id, attribute_values.participant_id, attribute_values.attribute_value)"
			+ " VALUES (?, ?, ?)";
	private static final String SQL_FOR_GETTING_ATTRIBUTE_VALUES_BY_ID = "SELECT * FROM attribute_values "
			+ "WHERE attribute_values.id = ? AND attribute_values.delete = 0";
	private static final String SQL_FOR_UPDATING_ATTRIBUTE_VALUES_BY_ID = "UPDATE attribute_values AS av "
			+ "SET av.attribute_value = ? WHERE av.id = ?";
	private static final String SQL_FOR_DELETING_ATTRIBUTE_VALUES_BY_ID = "UPDATE attribute_values AS av"
			+ " SET av.delete = 1 WHERE av.id = ?";
	private static final String SQL_FOR_GETTING_PARTICIPANT_ATTRIBUTES = "SELECT * FROM attribute_values AS av "
			+ "LEFT JOIN attributes AS a ON av.attribute_id=a.id LEFT JOIN `group` AS g ON a.group_id=g.id "
			+ "LEFT JOIN connect_group_participant AS c ON g.id=c.group_id AND c.participant_id=av.participant_id "
			+ "LEFT JOIN participant AS p ON c.participant_id=p.id WHERE g.id= ? and p.id= ? AND p.delete = 0";

	private class AttributeValuesRawMapper implements RowMapper<AttributeValues> {

		@Override
		public AttributeValues mapRow(ResultSet rs, int rowNum) throws SQLException {
			AttributeValues attributeValues = new AttributeValues();
			attributeValues.setId(rs.getInt(1));
			attributeValues.setAttributeId(rs.getInt(2));
			attributeValues.setParticipantId(rs.getInt(3));
			attributeValues.setValue(rs.getString(4));
			return attributeValues;
		}
	}

	/**
	 * Method for creating attribute values
	 * 
	 * @param attributeValues
	 * @return ResponseEntity
	 */
	@Override
	public void setAttributeValues(AttributeValues attributeValues) {

		try {

			jdbcTemplate.update(SQL_FOR_SETTING_ATTRIBUTE_VALUES, attributeValues.getAttributeId(),
					attributeValues.getParticipantId(), attributeValues.getValue());
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method for getting attribute values form the db
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@Override
	public AttributeValues getAttributeValuesById(Integer attributeValuesId) {

		try {

			List<AttributeValues> list = jdbcTemplate.query(SQL_FOR_GETTING_ATTRIBUTE_VALUES_BY_ID,
					new AttributeValuesRawMapper(), attributeValuesId);

			return list.isEmpty() ? null : list.get(0);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method for updating attribute values
	 * 
	 * @param attributeValues
	 * @return ResponseEntity
	 */
	@Override
	public void updateAttributeValuesById(AttributeValues attributeValues, Integer id) {

		try {

			jdbcTemplate.update(SQL_FOR_UPDATING_ATTRIBUTE_VALUES_BY_ID, attributeValues.getValue(), id);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method for deleting attribute values by id
	 * 
	 * @param attributeValuesId
	 * @return ResponseEntity
	 */
	@Override
	public void deleteAttributeValuesById(Integer attributeValuesId) {

		try {
			jdbcTemplate.update(SQL_FOR_DELETING_ATTRIBUTE_VALUES_BY_ID, attributeValuesId);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
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
	public List<AttributeValues> getParticipantAttributesInGroup(Integer groupId, Integer participantId) {

		try {

			List<AttributeValues> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_ATTRIBUTES,
					new AttributeValuesRawMapper(), groupId, participantId);

			return list.isEmpty() ? null : list;
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}
}
