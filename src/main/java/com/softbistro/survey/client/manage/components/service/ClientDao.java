package com.softbistro.survey.client.manage.components.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.components.interfaces.IClient;

/**
 * CRUD for entity Client
 * 
 * @author cortes
 *
 */
@Repository
public class ClientDao implements IClient {

	private static final String SELECT_BY_EMAIL = "SELECT * FROM clients  WHERE clients.email = ? and clients.`delete` = 0";
	private static final String FIND_CLIENT = "SELECT * FROM clients  WHERE clients.email = ? or clients.client_name = ? and clients.`delete` = 0";
	private static final String SAVE_CLIENT = "INSERT INTO clients (client_name, password, email) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE  email = email";
	private static final String SAVE_FACEBOOK_CLIENT = "INSERT INTO clients (client_name, facebook_id, email) VALUES(?, ?, ?)"
			+ "ON DUPLICATE KEY UPDATE facebook_id = ?";
	private static final String SAVE_GOOGLE_CLIENT = "INSERT INTO clients (client_name, google_id, email) VALUES(?, ?, ?)"
			+ "ON DUPLICATE KEY UPDATE google_id = ?";
	private static final String UPDATE_CLIENT = "UPDATE clients SET client_name = ?, email = ?, password = ? WHERE id = ?";
	private static final String DELETE_CLIENT = "UPDATE clients as sc SET sc.`delete` = '1' WHERE sc.id = ?";
	private static final String UPDATE_CLIENT_PASSWORD = "UPDATE clients SET password = ? WHERE id = ?";
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
	@Override
	public ResponseEntity<Client> findClientByEmail(String email) {

		try {

			List<Client> clientList = jdbc.query(SELECT_BY_EMAIL, new BeanPropertyRowMapper<>(Client.class), email);

			return clientList.isEmpty() ? new ResponseEntity<Client>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<Client>(clientList.get(0), HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<Object> saveClient(Client client) {

		try {

			if (findClientByLoginAndEmail(client).getBody() != null) {

				return new ResponseEntity<Object>(HttpStatus.OK);
			}

			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(SAVE_CLIENT, client.getClientName(), md5HexPassword, client.getEmail());
			return new ResponseEntity<Object>(HttpStatus.CREATED);

		} catch (Exception ex) {

			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Save information about client that authorized with help of social
	 * networks
	 * 
	 * @param client
	 * @return
	 */
	@Override
	public ResponseEntity<Object> saveSocialClient(Client client) {

		try {

			Client resultFindClient = (Client) findClientByEmail(client.getEmail()).getBody();

			if (resultFindClient == null) {

				return socialSaveClientNotExist(client);
			} else {

				return socialSaveClientExist(client, resultFindClient);
			}

		} catch (Exception ex) {

			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<Object> deleteClient(Integer id) {

		try {

			jdbc.update(DELETE_CLIENT, id);
		} catch (Exception ex) {

			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(HttpStatus.OK);
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
	public ResponseEntity<Object> updateClient(Client client, Integer id) {

		try {
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(UPDATE_CLIENT, client.getClientName(), client.getEmail(), md5HexPassword, id);
		} catch (Exception ex) {

			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(HttpStatus.OK);
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
	public ResponseEntity<Object> updatePassword(Client client, Integer id) {

		try {
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(UPDATE_CLIENT_PASSWORD, md5HexPassword, id);
		} catch (Exception ex) {

			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Find client by email and client name
	 * 
	 * @param client
	 * @return
	 */
	@Override
	public ResponseEntity<Client> findClientByLoginAndEmail(Client client) {

		try {

			List<Client> clientList = jdbc.query(FIND_CLIENT, new BeanPropertyRowMapper<>(Client.class),
					client.getEmail(), client.getClientName());

			return clientList.isEmpty() ? new ResponseEntity<Client>(HttpStatus.NO_CONTENT)
					: new ResponseEntity<Client>(clientList.get(0), HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update some fields in client when client authorized with help of social
	 * networks
	 * 
	 * @param client
	 * @return
	 */
	private ResponseEntity<Object> socialSaveClientNotExist(Client client) {

		if (client.getFlag().equals(FACEBOOK)) {

			jdbc.update(SAVE_FACEBOOK_CLIENT, client.getEmail(), client.getFacebookId(), client.getEmail(),
					client.getFacebookId());
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		if (client.getFlag().equals(GOOGLE)) {

			jdbc.update(SAVE_GOOGLE_CLIENT, client.getEmail(), client.getGoogleId(), client.getEmail(),
					client.getGoogleId());
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Save information about client that authorized with help of social
	 * networks when this client is exist in database
	 * 
	 * @param client
	 * @param resultFindClient
	 * @return
	 */
	private ResponseEntity<Object> socialSaveClientExist(Client client, Client resultFindClient) {

		if (client.getFlag().equals(FACEBOOK) && StringUtils.isBlank(resultFindClient.getFacebookId())) {

			jdbc.update(SAVE_FACEBOOK_CLIENT, client.getEmail(), client.getFacebookId(), client.getEmail(),
					client.getFacebookId());
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		if (client.getFlag().equals(GOOGLE) && StringUtils.isBlank(resultFindClient.getGoogleId())) {

			jdbc.update(SAVE_GOOGLE_CLIENT, client.getEmail(), client.getGoogleId(), client.getEmail(),
					client.getGoogleId());
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
