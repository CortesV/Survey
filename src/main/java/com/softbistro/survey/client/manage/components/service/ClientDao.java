package com.softbistro.survey.client.manage.components.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
	private static final String FIND_CLIENT = "SELECT * FROM survey.clients  WHERE survey.clients.email = ? or survey.clients.client_name = ? and survey.clients.`delete` = 0";
	private static final String SAVE_CLIENT = "INSERT INTO survey.clients (client_name, password, email, status) VALUES(?, ?, ?, 'NEW') ON DUPLICATE KEY UPDATE  email = email";
	private static final String SAVE_FACEBOOK_CLIENT = "INSERT INTO survey.clients (client_name, password, facebook_id, email, status) VALUES(?, ?, ?, ?, 'NEW')"
			+ "ON DUPLICATE KEY UPDATE facebook_id = ?";
	private static final String SAVE_GOOGLE_CLIENT = "INSERT INTO survey.clients (client_name, password, google_id, email, status) VALUES(?, ?, ?, ?, 'NEW')"
			+ "ON DUPLICATE KEY UPDATE google_id = ?";
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
	private static final String DESCRIPTION_SAVE = "Client successfully saved";
	private static final String DESCRIPTION_SOC_SAVE = "Client from social network successfully saved";
	private static final String NOT_FOUND_CLIENT = "Client with this email isn't found";
	private static final String EXIST_CLIENT = "Dyplicate client name or email.";
	private static final String EXIST_SOC_CLIENT = "This email has already exist";
	private static final String FACEBOOK = "facebook";
	private static final String GOOGLE = "google";

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

			return clientList.isEmpty() ? new Response(null, HttpStatus.OK, NOT_FOUND_CLIENT)
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

			List<Client> clientList = (List<Client>) findClientByLoginAndEmail(client).getData();

			if (clientList != null) {

				return new Response(null, HttpStatus.OK, EXIST_CLIENT);
			}

			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(SAVE_CLIENT, client.getClientName(), md5HexPassword, client.getEmail());
			return new Response(null, HttpStatus.CREATED, DESCRIPTION_SAVE);

		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

	}

	@Override
	public Response saveSocialClient(Client client) {

		try {
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());

			Client resultFindClient = (Client) findClientByEmail(client.getEmail()).getData();

			if (client.getFlag().equals(FACEBOOK) && StringUtils.isBlank(resultFindClient.getFacebookId())) {

				jdbc.update(SAVE_FACEBOOK_CLIENT, client.getEmail(), md5HexPassword, client.getFacebookId(),
						client.getEmail(), client.getFacebookId());
				return new Response(null, HttpStatus.CREATED, DESCRIPTION_SOC_SAVE);
			}

			if (client.getFlag().equals(GOOGLE) && StringUtils.isBlank(resultFindClient.getGoogleId())) {

				jdbc.update(SAVE_GOOGLE_CLIENT, client.getEmail(), md5HexPassword, client.getGoogleId(),
						client.getEmail(), client.getGoogleId());
				return new Response(null, HttpStatus.CREATED, DESCRIPTION_SOC_SAVE);
			}

			
			return new Response(null, HttpStatus.OK, EXIST_SOC_CLIENT);

		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Response findClientByLoginAndEmail(Client client) {

		try {

			List<Client> clientList = jdbc.query(FIND_CLIENT, new BeanPropertyRowMapper(Client.class),
					client.getEmail(), client.getClientName());

			return clientList.isEmpty() ? new Response(null, HttpStatus.OK, NOT_FOUND_CLIENT)
					: new Response(clientList, HttpStatus.OK, null);

		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

}
