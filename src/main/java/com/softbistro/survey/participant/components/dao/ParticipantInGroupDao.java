package com.softbistro.survey.participant.components.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.participant.components.interfaces.IParticipantInGroup;
import com.softbistro.survey.response.Response;

/**
 * Data access object for participant in group
 * 
 * @author af150416
 *
 */
@Repository
public class ParticipantInGroupDao implements IParticipantInGroup {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private final static String SQL_FOR_ADDING_PARTICIPANT_IN_GROUP = "INSERT INTO survey.connect_group_participant (survey.connect_group_participant.group_id, survey.connect_group_participant.participant_id) VALUES (?, ?)";
	private final static String SQL_FOR_DELETING_PARTICIPANT_IN_GROUP = "UPDATE survey.connect_group_participant AS c SET c.status = 'DELETE' WHERE c.group_id = ? AND c.participant_id = ?";
	private final static String SQL_FOR_GETTING_PARTICIPANTS_BY_GROUP_ID = "SELECT * FROM survey.participant AS p LEFT JOIN survey.connect_group_participant AS c ON c.participant_id=p.id WHERE p.id= ?";
	private final static String SQL_FOR_GETTING_PARTICIPANT_GROUPS = "SELECT * FROM survey.group AS g LEFT JOIN survey.connect_group_participant AS c ON g.id=c.group_id WHERE g.id = ?";

	/**
	 * Method for getting all participant by group
	 * 
	 * @param groupId
	 * @return Response
	 */
	@Override
	public Response getParticipantsByGroup(Integer groupId) {
		try {
			return new Response(jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANTS_BY_GROUP_ID,
					new BeanPropertyRowMapper<>(Participant.class), groupId), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for adding participant in group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@Override
	public Response addParticipantInGroup(ParticipantInGroup participantInGoup) {
		try {
			jdbcTemplate.update(SQL_FOR_ADDING_PARTICIPANT_IN_GROUP, participantInGoup.getGroupId(),
					participantInGoup.getParticipantId());
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for deleting participant from group
	 * 
	 * @param groupId
	 * @param participantId
	 * @return Response
	 */
	@Override
	public Response deletingParticipantfromGroup(Integer groupId, Participant participantId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_PARTICIPANT_IN_GROUP, groupId, participantId);
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for getting all participant groups
	 * 
	 * @param participantId
	 * @return Response
	 */
	@Override
	public Response getParticipantGroups(Integer participantId) {

		try {
			return new Response(jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_GROUPS,
					new BeanPropertyRowMapper<>(Group.class), participantId), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
