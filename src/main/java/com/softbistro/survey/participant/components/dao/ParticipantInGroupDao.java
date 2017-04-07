package com.softbistro.survey.participant.components.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private final static String SQL_FOR_ADDING_PARTICIPANT_IN_GROUP = "INSERT INTO connect_group_participant "
			+ "(connect_group_participant.group_id, connect_group_participant.participant_id) VALUES (?, ?)";
	private final static String SQL_FOR_DELETING_PARTICIPANT_IN_GROUP = "UPDATE connect_group_participant AS c "
			+ "SET c.delete = 1 WHERE c.group_id = ? AND c.participant_id = ?";
	private final static String SQL_FOR_GETTING_PARTICIPANTS_BY_GROUP_ID = "SELECT * FROM participant AS p "
			+ "LEFT JOIN connect_group_participant AS c ON c.participant_id=p.id WHERE c.group_id = ?  AND p.delete = 0";
	private final static String SQL_FOR_GETTING_PARTICIPANT_GROUPS = "SELECT g.id, g.client_id, group_name FROM `group` AS g "
			+ "LEFT JOIN connect_group_participant AS c ON g.id=c.group_id WHERE c.participant_id = ? AND g.delete = 0";

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<List<Participant>> getParticipantsByGroup(Integer groupId) {
		try {
			List<Participant> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANTS_BY_GROUP_ID,
					new BeanPropertyRowMapper<>(Participant.class), groupId);

			return list.isEmpty() ? new ResponseEntity<List<Participant>>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<List<Participant>>(list, HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<List<Participant>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<Object> addParticipantInGroup(ParticipantInGroup participantInGoup) {
		try {
			jdbcTemplate.update(SQL_FOR_ADDING_PARTICIPANT_IN_GROUP, participantInGoup.getGroupId(),
					participantInGoup.getParticipantId());
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<Object> deletingParticipantfromGroup(Integer groupId, Integer participantId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_PARTICIPANT_IN_GROUP, groupId, participantId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity<List<Group>> getParticipantGroups(Integer participantId) {
		try {
			List<Group> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_GROUPS,
					new BeanPropertyRowMapper<>(Group.class), participantId);

			return list.isEmpty() ? new ResponseEntity<List<Group>>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<List<Group>>(list, HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<List<Group>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
