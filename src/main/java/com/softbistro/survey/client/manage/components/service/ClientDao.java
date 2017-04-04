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
import com.softbistro.survey.client.manage.service.FindClientService;
import com.softbistro.survey.response.Response;

/**
 * CRUD for entity Client
 * 
 * @author cortes
 *
 */
@Repository
public class ClientDao implements IClient {

	private static final String SELECT_CLIENT_FIRST_PART = "SELECT * FROM clients  WHERE clients.";
	private static final String SELECT_CLIENT_SECOND_PART = " = ? and clients.`delete` = 0";
	private static final String FIND_CLIENT_BY_ID = "SELECT * FROM clients  WHERE clients.id = ? and clients.`delete` = 0";
	private static final String FIND_CLIENT = "SELECT * FROM clients  WHERE clients.email = ? or clients.client_name = ? and clients.`delete` = 0";
	private static final String SAVE_CLIENT = "INSERT INTO clients (client_name, password, email) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE  email = email";
	private static final String SAVE_FACEBOOK_CLIENT = "INSERT INTO clients (client_name, facebook_id, email) VALUES(?, ?, ?)";
	private static final String SAVE_GOOGLE_CLIENT = "INSERT INTO clients (client_name, google_id, email) VALUES(?, ?, ?)";
	private static final String UPDATE_CLIENT = "UPDATE clients SET client_name = ?, email = ?, password = ?, facebook_id = ?, google_id = ? WHERE id = ?";
	private static final String DELETE_CLIENT = "UPDATE clients as sc SET sc.`delete` = '1' WHERE sc.id = ?";
	private static final String UPDATE_CLIENT_PASSWORD = "UPDATE clients SET password = ? WHERE id = ?";
	private static final String DESCRIPTION_SAVE = "Client successfully saved";
	private static final String DESCRIPTION_SOC_SAVE = "Client from social network successfully saved";
	private static final String NOT_FOUND_CLIENT = "Client with this email isn't found";
	private static final String EXIST_CLIENT = "Dyplicate client name or email.";
	private static final String EXIST_SOC_CLIENT = "This email has already exist";
	private static final String FACEBOOK = "facebook";
	private static final String GOOGLE = "google";

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	FindClientService findClientService;

	/**
	 * Find client in database by id of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	@Override
	public Response findClient(Integer id) {

		try {

			Client client = (Client) jdbc.queryForObject(FIND_CLIENT_BY_ID, new BeanPropertyRowMapper(Client.class),
					id);

			return client == null ? new Response(null, HttpStatus.OK, NOT_FOUND_CLIENT)
					: new Response(client, HttpStatus.OK, null);

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - information about of client
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

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
	public Response saveSocialClient(Client client) {

		try {

			Client resultFindClient = findClientService.findClient(client);

			if (resultFindClient == null) {

				return socialSaveClientNotExist(client);
			} else {

				return socialSaveClientExist(client, resultFindClient);
			}

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
			jdbc.update(UPDATE_CLIENT, client.getClientName(), client.getEmail(), md5HexPassword,
					client.getFacebookId(), client.getGoogleId(), id);
		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

		return new Response(null, HttpStatus.OK, null);
	}

	/**
	 * Find client by email and client name
	 * 
	 * @param client
	 * @return
	 */
	@Override
	public Response findClientByLoginAndEmail(Client client) {

		try {

			List<Client> clientList = jdbc.query(FIND_CLIENT, new BeanPropertyRowMapper(Client.class),
					client.getEmail(), client.getClientName());

			return clientList.isEmpty() ? new Response(null, HttpStatus.OK, NOT_FOUND_CLIENT)
					: new Response(clientList, HttpStatus.OK, null);

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Update some fields in client when client authorized with help of social
	 * networks
	 * 
	 * @param client
	 * @return
	 */
	private Response socialSaveClientNotExist(Client client) {

		try {

			Client resultFindClient = findClientService.findByEmail(client);
			
			if(resultFindClient != null){
				
				return new Response(client, HttpStatus.OK, EXIST_SOC_CLIENT);
			}
			
			if (client.getFlag().equals(FACEBOOK)) {

				jdbc.update(SAVE_FACEBOOK_CLIENT, client.getClientName(), client.getFacebookId(), client.getEmail());
				return new Response(client, HttpStatus.CREATED, DESCRIPTION_SOC_SAVE);
			}

			if (client.getFlag().equals(GOOGLE)) {

				jdbc.update(SAVE_GOOGLE_CLIENT, client.getClientName(), client.getGoogleId(), client.getEmail());
				return new Response(client, HttpStatus.CREATED, DESCRIPTION_SOC_SAVE);
			}

			return new Response(client, HttpStatus.OK, EXIST_SOC_CLIENT);

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Save information about client that authorized with help of social
	 * networks when this client is exist in database
	 * 
	 * @param client
	 * @param resultFindClient
	 * @return
	 */
	private Response socialSaveClientExist(Client client, Client resultFindClient) {

		try {

			if (client.getFlag().equals(FACEBOOK) && StringUtils.isNotBlank(resultFindClient.getFacebookId())
					&& !resultFindClient.getEmail().equals(client.getEmail())) {

				resultFindClient.setEmail(client.getEmail());
				jdbc.update(UPDATE_CLIENT, resultFindClient.getClientName(), resultFindClient.getEmail(), resultFindClient.getPassword(),
						resultFindClient.getFacebookId(), resultFindClient.getGoogleId(), resultFindClient.getId());
				return new Response(client, HttpStatus.CREATED, DESCRIPTION_SOC_SAVE);
			}

			if (client.getFlag().equals(GOOGLE) && StringUtils.isNotBlank(resultFindClient.getGoogleId())
					&& !resultFindClient.getEmail().equals(client.getEmail())) {

				resultFindClient.setEmail(client.getEmail());
				jdbc.update(UPDATE_CLIENT, resultFindClient.getClientName(), resultFindClient.getEmail(), resultFindClient.getPassword(),
						resultFindClient.getFacebookId(), resultFindClient.getGoogleId(), resultFindClient.getId());
				return new Response(client, HttpStatus.CREATED, DESCRIPTION_SOC_SAVE);
			}

			return new Response(client, HttpStatus.OK, EXIST_SOC_CLIENT);

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Find client by email, facebookId or googleId
	 * 
	 * @param template
	 *            template - email, facebookId or googleId
	 * @param value
	 *            value - value of template
	 * @return return - information about of client
	 */
	@Override
	public Response findByTemplate(String template, String value) {

		try {

			List<Client> clientList = jdbc.query(SELECT_CLIENT_FIRST_PART + template + SELECT_CLIENT_SECOND_PART,
					new BeanPropertyRowMapper(Client.class), value);

			return clientList.isEmpty() ? new Response(null, HttpStatus.OK, NOT_FOUND_CLIENT)
					: new Response(clientList.get(0), HttpStatus.OK, null);

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

}
