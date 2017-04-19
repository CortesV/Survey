package com.softbistro.survey.participant.components.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.participant.components.interfaces.IParticipantInGroup;

/**
 * Data access object for participant in group
 * 
 * @author af150416
 *
 */
@Repository
public class ParticipantInGroupDao implements IParticipantInGroup {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = Logger.getLogger(ParticipantInGroupDao.class);

	private static final String SQL_FOR_ADDING_PARTICIPANT_IN_GROUP = "INSERT INTO connect_group_participant "
			+ "(connect_group_participant.group_id, connect_group_participant.participant_id) VALUES (?, ?)";
	private static final String SQL_FOR_DELETING_PARTICIPANT_IN_GROUP = "UPDATE connect_group_participant AS c "
			+ "SET c.delete = 1 WHERE c.group_id = ? AND c.participant_id = ?";

	private static final String SQL_FOR_GETTING_PARTICIPANTS_BY_GROUP_ID = "SELECT p.id, p.client_id, p.email, p.first_name, "
			+ "p.last_name FROM participant AS p LEFT JOIN connect_group_participant AS cgp ON cgp.participant_id = p.id "
			+ "LEFT JOIN `group` AS g ON g.id = cgp.group_id WHERE g.id = ? AND p.`delete` = 0";

	private static final String SQL_FOR_GETTING_PARTICIPANT_GROUPS = "SELECT g.id, g.client_id, group_name FROM `group` AS g "
			+ "LEFT JOIN connect_group_participant AS c ON g.id=c.group_id WHERE c.participant_id = ? AND g.delete = 0";

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public List<Participant> getParticipantsByGroup(Integer groupId) {

		try {

			List<Participant> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANTS_BY_GROUP_ID,
					new BeanPropertyRowMapper<>(Participant.class), groupId);

			return list.isEmpty() ? null : list;
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method for adding participants in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@Override
	public void addParticipantInGroup(List<ParticipantInGroup> participantInGoup) {

		try {

			jdbcTemplate.batchUpdate(SQL_FOR_ADDING_PARTICIPANT_IN_GROUP, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ParticipantInGroup connectParticipantGroup = participantInGoup.get(i);
					ps.setInt(1, connectParticipantGroup.getGroupId());
					ps.setInt(2, connectParticipantGroup.getParticipantId());
				}

				@Override
				public int getBatchSize() {
					return participantInGoup.size();
				}
			});

		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@Override
	public void deletingParticipantfromGroup(Integer groupId, Integer participantId) {

		try {

			jdbcTemplate.update(SQL_FOR_DELETING_PARTICIPANT_IN_GROUP, groupId, participantId);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	@Override
	public List<Group> getParticipantGroups(Integer participantId) {
		try {
			List<Group> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_GROUPS,
					new BeanPropertyRowMapper<>(Group.class), participantId);

			return list.isEmpty() ? null : list;
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}
}
