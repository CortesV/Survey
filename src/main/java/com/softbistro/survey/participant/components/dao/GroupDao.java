package com.softbistro.survey.participant.components.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	private static final Logger LOGGER = Logger.getLogger(GroupDao.class);

	private static final String SQL_FOR_SETTING_GROUP = "INSERT INTO `group` (`group`.client_id, `group`.group_name) VALUES (?, ?)";
	private static final String SQL_FOR_GETTING_GROUP_BY_ID = "SELECT * FROM `group` AS g WHERE g.id = ? AND g.`delete` = 0";
	private static final String SQL_FOR_GETTING_GROUP_BY_CLIENT = "SELECT * FROM `group` AS g WHERE g.client_id = ? AND g.`delete` = 0";
	private static final String SQL_FOR_UPDATING_GROUP_BY_ID = "UPDATE `group` AS g SET g.group_name = ? WHERE g.id = ?";
	private static final String SQL_FOR_DELETING_GROUP_BY_ID = "UPDATE `group` AS g SET g.delete = 1 WHERE g.id = ?";

	/**
	 * Method to create group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	@Override
	public void setGroup(Group group) {

		try {

			jdbcTemplate.update(SQL_FOR_SETTING_GROUP, group.getClientId(), group.getGroupName());
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method to get group from db
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public Group getGroupByid(Integer groupId) {

		try {

			List<Group> list = jdbcTemplate.query(SQL_FOR_GETTING_GROUP_BY_ID, new BeanPropertyRowMapper<>(Group.class),
					groupId);

			return list.isEmpty() ? null : list.get(0);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method to get all clients in group
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	@Override
	public List<Group> getGroupsByClient(Integer clientId) {

		try {

			List<Group> list = jdbcTemplate.query(SQL_FOR_GETTING_GROUP_BY_CLIENT,
					new BeanPropertyRowMapper<>(Group.class), clientId);

			return list.isEmpty() ? null : list;
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method to update group
	 * 
	 * @param group
	 * @return ResponseEntity
	 */
	@Override
	public void updateGroupById(Group group, Integer id) {

		try {

			jdbcTemplate.update(SQL_FOR_UPDATING_GROUP_BY_ID, group.getGroupName(), id);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method for deleting group by id
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public void deleteGroupById(Integer groupId) {

		try {

			jdbcTemplate.update(SQL_FOR_DELETING_GROUP_BY_ID, groupId);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}
}
