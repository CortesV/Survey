package com.softbistro.survey.participant.components.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
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

	private static final Logger LOGGER = Logger.getLogger(AttributesDao.class);

	private static final String SQL_FOR_SETTING_ATTRIBUTES = "INSERT INTO attributes (attributes.group_id, attributes.attribute) VALUES (?, ?)";
	private static final String SQL_FOR_GETTING_ATTRIBUTES_BY_ID = "SELECT * FROM attributes AS at WHERE at.id = ? AND at.delete = 0";
	private static final String SQL_FOR_UPDATING__ATTRIBUTES_BY_ID = "UPDATE attributes AS at SET at.group_id = ?, at.attribute = ? WHERE at.id = ?";
	private static final String SQL_FOR_DELETING_ATTRIBUTES = "UPDATE attributes AS at SET at.delete = 1 WHERE at.id = ?";
	private static final String SQL_FOR_GETTING_ATTRIBUTES_BY_GROUP = "SELECT * FROM attributes WHERE attributes.group_id = ? AND attributes.delete = 0";

	/**
	 * Method for creating the attribute value
	 * 
	 * @param Attributes
	 * @return ResponseEntity
	 */
	@Override
	public Integer setAttribute(Attributes attributes) {

		try {

			KeyHolder holder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement preparedStatement = connection.prepareStatement(SQL_FOR_SETTING_ATTRIBUTES,
							Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setInt(1, attributes.getGroupId());
					preparedStatement.setString(2, attributes.getAttribute());
					
					return preparedStatement;
				}
			}, holder);

			return holder.getKey().intValue();
			
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method for getting attribute by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	@Override
	public Attributes getAttributeById(Integer attributesId) {

		try {

			List<Attributes> list = jdbcTemplate.query(SQL_FOR_GETTING_ATTRIBUTES_BY_ID,
					new BeanPropertyRowMapper<>(Attributes.class), attributesId);

			return list.isEmpty() ? null : list.get(0);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method for updating attribute
	 * 
	 * @param Attributes
	 * @return ResponseEntity
	 */
	@Override
	public void updateAttributes(Attributes attributes, Integer attributesId) {

		try {

			jdbcTemplate.update(SQL_FOR_UPDATING__ATTRIBUTES_BY_ID, attributes.getGroupId(), attributes.getAttribute(),
					attributesId);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method for deleting attributes by id
	 * 
	 * @param attributesId
	 * @return ResponseEntity
	 */
	@Override
	public void deleteAttributes(Integer attributesId) {

		try {

			jdbcTemplate.update(SQL_FOR_DELETING_ATTRIBUTES, attributesId);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method to getting all attributes in group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public List<Attributes> getAttributesByGroup(Integer groupId) {

		List<Attributes> list = new ArrayList<>();
		try {

			list = jdbcTemplate.query(SQL_FOR_GETTING_ATTRIBUTES_BY_GROUP,
					new BeanPropertyRowMapper<>(Attributes.class), groupId);

			return list.isEmpty() ? null : list;
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return list;
		}
	}
}
