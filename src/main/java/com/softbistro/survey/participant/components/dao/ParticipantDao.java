package com.softbistro.survey.participant.components.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.interfaces.IParticipant;
import com.softbistro.survey.response.Response;

/**
 * Data access object for participant entity
 * 
 * @author af150416
 *
 */
@Repository
public class ParticipantDao implements IParticipant {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final static String SQL_FOR_SETTING_PARTICIPANT = "INSERT INTO survey.participant "
			+ "(survey.participant.first_name, survey.participant.last_name, survey.participant.email) "
			+ "VALUES (?, ?, ?)";
	private final static String SQL_FOR_UPDATING_PARTICIPANT = "UPDATE survey.participant AS p "
			+ "SET p.first_name= ?, p.last_name= ?, p.email = ?, WHERE p.id= ?";
	private final static String SQL_FOR_DELETING_PARTICIPANT = "UPDATE survey.participant AS p "
			+ "left join survey.connect_group_participant AS c on c.group_id=p.id "
			+ "left join survey.attribute_values AS av on av.participant_id=p.id left join survey.answers AS a on a.participant_id=p.id "
			+ "SET p.delete = 0, c.delete = 0, av.delete = 0, a.delete = 0 WHERE p.id = ?";
	private final static String SQL_FOR_GETTING_PARTICIPANT_BY_ID = "SELECT * FROM survey.participant WHERE survey.participant.id= ? "
			+ "AND survey.participant.delete = 0";
	private final static String SQL_FOR_GETTING_PARTICIPANT_BY_ATTRIBUTE_VALUE = "SELECT * FROM survey.participant AS p "
			+ "LEFT JOIN survey.attribute_values AS av ON av.participant_id=p.id LEFT JOIN survey.attributes AS at "
			+ "ON at.id=av.attribute_id WHERE at.id = ? AND av.attribute_value = ? AND p.delete = 0 AND av.delete = 0 AND at.delete = 0";
	private final static String SQL_FOR_GETTING_PARTICIPANT_BY_EMAIL_AND_CLIENT_ID = "SELECT * FROM survey.participant AS p "
			+ "LEFT JOIN survey.connect_group_participant AS c ON c.participant_id=p.id LEFT JOIN survey.group AS g ON g.id=c.group_id "
			+ "WHERE p.email= ? AND g.client_id = ? AND p.delete = 0";

	/**
	 * Method for creating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	@Override
	public Response setParticipant(Participant participant) {
		try {
			jdbcTemplate.update(SQL_FOR_SETTING_PARTICIPANT, participant.getFirstName(), participant.getLastName(),
					participant.geteMail());
			return new Response(null, HttpStatus.CREATED, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for updating participant
	 * 
	 * @param participant
	 * @return Response
	 */
	@Override
	public Response updateParticipant(Participant participant) {
		try {
			jdbcTemplate.update(SQL_FOR_UPDATING_PARTICIPANT, participant.getFirstName(), participant.getLastName(),
					participant.geteMail(), participant.getId());
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method for deleting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	@Override
	public Response deleteParticipantById(Integer participantId) {
		try {
			jdbcTemplate.update(SQL_FOR_DELETING_PARTICIPANT, participantId);
			return new Response(null, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to getting participant from db by id
	 * 
	 * @param participantId
	 * @return Response
	 */
	@Override
	public Response getParticipantById(Integer participantId) {
		try {
			List<Participant> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_BY_ID,
					new BeanPropertyRowMapper<>(Participant.class), participantId);

			return list.isEmpty() ? new Response(null, HttpStatus.OK, "No such element")
					: new Response(list, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to getting participant from db by email and client id
	 * 
	 * @param email,
	 *            clientId
	 * @return Response
	 */
	@Override
	public Response getParticipantByEmailAndClientId(String email, Integer clientId) {
		try {
			List<Participant> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_BY_EMAIL_AND_CLIENT_ID,
					new BeanPropertyRowMapper<>(Participant.class), email, clientId);

			return list.isEmpty() ? new Response(null, HttpStatus.OK, "No such element")
					: new Response(list.get(0), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Method to getting participant from db by attribute value
	 * 
	 * @param attributeId,
	 *            attributeValue
	 * @return Response
	 */
	@Override
	public Response getParticipantByAttributeValue(Integer attributeId, String attributeValue) {
		try {
			List<Participant> list = jdbcTemplate.query(SQL_FOR_GETTING_PARTICIPANT_BY_ATTRIBUTE_VALUE,
					new BeanPropertyRowMapper<>(Participant.class), attributeId, attributeValue);

			return list.isEmpty() ? new Response(null, HttpStatus.OK, "No such elements")
					: new Response(list, HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
