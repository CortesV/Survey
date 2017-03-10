package com.survey.softbistro.components.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.survey.softbistro.components.entity.AuthorityName;
import com.survey.softbistro.components.entity.Client;
import com.survey.softbistro.components.entity.ResponseStatus;
import com.survey.softbistro.components.interfaces.IClient;

/**
 * CRUD for entity Client
 * 
 * @author cortes
 *
 */
@Repository
public class ClientDao implements IClient {

	private static final String SELECT_BY_EMAIL = "SELECT * FROM survey.clients  WHERE email = ?";
	private static final String SAVE_CLIENT = "INSERT INTO survey.clients (client_name, password, email, status) VALUES(?, ?, ?, 'NEW')";
	private static final String UPDATE_CLIENT = "UPDATE survey.clients SET client_name = ?, email = ?, password = ? WHERE email = ?";
	private static final String SELECT_CLIENT_ROLES = "SELECT sa.name_authority FROM survey.client_role as scr inner join survey.clients as sc on sc.id = scr.client_id "
			+ "inner join survey.authority as sa on sa.id = scr.authority_id where sc.id = ?";
	private static final String DELETE_CLIENT = "DELETE survey.clients, survey.`group`, survey.survey, survey.connect_group_survey, survey.connect_group_participant, survey.attributes, "
			+ "survey.attribute_values, survey.participant, survey.answers, survey.questions, survey.question_sections, survey.client_role FROM survey.clients, survey.`group`, "
			+ "survey.survey, survey.connect_group_survey, survey.connect_group_participant, survey.attributes, survey.attribute_values, survey.participant, survey.answers, "
			+ "survey.questions, survey.question_sections, survey.client_role WHERE survey.clients.id = survey.`group`.client_id and survey.clients.id = survey.survey.client_id "
			+ "and survey.connect_group_survey.survey_id = survey.survey.id and survey.connect_group_survey.group_id = survey.`group`.id and "
			+ "survey.connect_group_participant.group_id = survey.`group`.id and survey.connect_group_participant.participant_id = survey.participant.id "
			+ "and survey.attributes.group_id = survey.`group`.id and survey.attribute_values.attribute_id = survey.attributes.id "
			+ "and survey.attribute_values.participant_id = survey.participant.id and survey.answers.participant_id = survey.participant.id "
			+ "and survey.answers.question_id = survey.questions.id and survey.questions.question_section_id = survey.question_sections.id "
			+ "and survey.questions.survey_id = survey.survey.id and survey.question_sections.survey_id = survey.survey.id "
			+ "and survey.client_role.client_id = survey.clients.id and survey.clients.id = ?";

	@Autowired
	private JdbcTemplate jdbc;

	/**
	 * Class for geting clients from database
	 * 
	 * @author cortes
	 *
	 */
	public class WorkingWithRowMap implements RowMapper<Client> {

		@Override
		public Client mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Client client = new Client();
			client.setId(resultSet.getLong(1));
			client.setClientName(resultSet.getString(2));
			client.setEmail(resultSet.getString(3));
			client.setPassword(resultSet.getString(4));
			client.setEnabled(Boolean.parseBoolean(resultSet.getString(6)));
			client.setLastPasswordResetDate(resultSet.getDate(7));
			return client;
		}
	}

	/**
	 * Class for geting authorities of client from database
	 * 
	 * @author cortes
	 *
	 */
	public class GetRowMap implements RowMapper<AuthorityName> {

		@Override
		public AuthorityName mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			if (resultSet.getString(1).equals("ROLE_USER")) {
				return AuthorityName.ROLE_USER;
			} else
				return AuthorityName.ROLE_ADMIN;

		}
	}

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	@Override
	public Client findClientByEmail(String email) {

		Client client = jdbc.queryForObject(SELECT_BY_EMAIL, new WorkingWithRowMap(), email);
		client.setAuthorities(jdbc.query(SELECT_CLIENT_ROLES, new GetRowMap(), client.getId()));
		return client;
	}

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	@Override
	public Map<String, String> saveClient(Client client) {

		return ResponseStatus.returnResponseStatus(
				jdbc.update(SAVE_CLIENT, client.getClientName(), client.getPassword(), client.getEmail()));

	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	@Override
	public Map<String, String> deleteClient(String email) {

		Client client = jdbc.queryForObject(SELECT_BY_EMAIL, new WorkingWithRowMap(), email);
		return ResponseStatus.returnResponseStatus(jdbc.update(DELETE_CLIENT, client.getId()));
	}

	/**
	 * Update information of client
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @param oldEmail
	 *            oldEmail - email of client that used for authorization
	 * @param oldPassword
	 *            password - email of client that used for authorization
	 * @return return - status of execution this method
	 */
	@Override
	public Map<String, String> updateClient(Client client, String oldEmail, String oldPassword) {

		Client oldClient = jdbc.queryForObject(SELECT_BY_EMAIL, new WorkingWithRowMap(), oldEmail);
		if (!oldClient.getPassword().equals(oldPassword)) {
			return new HashMap<>();
		}

		return ResponseStatus.returnResponseStatus(
				jdbc.update(UPDATE_CLIENT, client.getClientName(), client.getEmail(), client.getPassword(), oldEmail));

	}

}
