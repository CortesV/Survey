package com.softbistro.survey.participant.components.dao;

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
			+ "(survey.participant.first_name, survey.participant.last_name, survey.participant.email, survey.participant.password) VALUES (?, ?, ?, ?)";

	private final static String SQL_FOR_UPDATING_PARTICIPANT = "UPDATE survey.participant AS p SET p.first_name= ?, p.last_name= ?, p.email = ?, p.password= ? WHERE p.id= ?";

	private final static String SQL_FOR_DELETING_PARTICIPANT = "UPDATE survey.participant AS p left join survey.connect_group_participant AS c on c.group_id=p.id "
			+ "left join survey.attribute_values AS av on av.participant_id=p.id left join survey.answers AS a on a.participant_id=p.id "
			+ "SET p.status = 'DELETE', c.status = 'DELETE', av.status = 'DELETE', a.status = 'DELETE' WHERE p.id = ?";

	private final static String SQL_FOR_GETTING_PARTICIPANT = "SELECT * FROM survey.participant WHERE survey.participant.id= ? AND survey.participant.status != 'DELETE'";

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
					participant.geteMail(), participant.getPassword());
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
					participant.geteMail(), participant.getPassword(), participant.getId());
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
			return new Response(jdbcTemplate.queryForObject(SQL_FOR_GETTING_PARTICIPANT,
					new BeanPropertyRowMapper<>(Participant.class), participantId), HttpStatus.OK, null);
		}

		catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
