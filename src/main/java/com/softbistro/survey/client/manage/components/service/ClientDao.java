package com.softbistro.survey.client.manage.components.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.components.interfaces.IClient;
import com.softbistro.survey.response.Response;

/**
 * CRUD for entity Client
 * 
 * @author cortes
 *
 */
@Repository
public class ClientDao implements IClient {

	private static final String SELECT_BY_EMAIL = "SELECT * FROM survey.clients  WHERE survey.clients.email = ? and survey.clients.`delete` = 0";
	private static final String SAVE_CLIENT = "INSERT INTO survey.clients (client_name, password, email, status) VALUES(?, ?, ?, 'NEW')";
	private static final String SAVE_SOCIAL_CLIENT = "INSERT INTO survey.clients (client_name, password, social_key, email, status) VALUES(?, ?, ?, ?, 'NEW')";
	private static final String UPDATE_CLIENT = "UPDATE survey.clients SET client_name = ?, email = ?, password = ? WHERE id = ?";
	private static final String DELETE_CLIENT = "UPDATE survey.clients as sc LEFT JOIN survey.survey as ss on ss.client_id = sc.id LEFT JOIN survey.`group` as sg on "
			+ "sg.client_id = sc.id LEFT JOIN survey.connect_group_participant as cgp on cgp.group_id = sg.id LEFT JOIN survey.participant as sp on "
			+ "sp.id = cgp.participant_id LEFT JOIN survey.attributes as sa on sa.group_id = sg.id LEFT JOIN survey.attribute_values as sav on "
			+ "sav.attribute_id = sa.id LEFT JOIN survey.answers as answ on answ.participant_id = sp.id LEFT JOIN survey.questions as sq on "
			+ "sq.survey_id = ss.id LEFT JOIN survey.question_sections as qs on qs.survey_id = ss.id LEFT JOIN survey.connect_group_survey as cgs on "
			+ "cgs.survey_id = ss.id LEFT JOIN survey.client_role as cr on cr.client_id = sc.id "
			+ "SET sc.`delete` = '1', ss.`delete` = '1', sg.`delete` = '1', cgp.`delete` = '1', sa.`delete` = '1', sp.`delete` = '1', "
			+ "sav.`delete` = '1', answ.`delete` = '1', sq.`delete` = '1', qs.`delete` = '1', cgs.`delete` = '1', cr.`delete` = '1'"
			+ " WHERE sc.id = ?";
	private static final String UPDATE_CLIENT_PASSWORD = "UPDATE survey.clients SET password = ? WHERE id = ?";

	@Autowired
	private JdbcTemplate jdbc;

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Response findClientByEmail(String email) {

		try {

			List<Client> clientList = jdbc.query(SELECT_BY_EMAIL, new BeanPropertyRowMapper(Client.class), email);

			return clientList.isEmpty() ? new Response(null, HttpStatus.OK, "No such this client")
					: new Response(clientList.get(0), HttpStatus.OK, null);

		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

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
	public Response saveClient(Client client) {

		try {
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(SAVE_CLIENT, client.getClientName(), md5HexPassword, client.getEmail());
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.CREATED, null);

	}

	@Override
	public Response saveSocialClient(Client client) {

		try {
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(SAVE_SOCIAL_CLIENT, client.getEmail(), md5HexPassword, client.getSocialKey(),
					client.getEmail());
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.CREATED, null);

	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	@Override
	public Response deleteClient(Integer id) {

		try {

			jdbc.update(DELETE_CLIENT, id);
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.OK, null);
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
	public Response updateClient(Client client, Integer id) {

		try {
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(UPDATE_CLIENT, client.getClientName(), client.getEmail(), md5HexPassword, id);
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.OK, null);
	}

	/**
	 * Update client's password
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @param id
	 *            id - id of client
	 * 
	 * @return return - status of execution this method
	 */
	@Override
	public Response updatePassword(Client client, Integer id) {

		try {
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(UPDATE_CLIENT_PASSWORD, md5HexPassword, id);
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.OK, null);
	}

}
