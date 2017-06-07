package com.softbistro.survey.client.manage.components.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.components.entity.ClientForSending;
import com.softbistro.survey.client.manage.components.interfaces.IClient;
import com.softbistro.survey.client.manage.service.FindClientService;
import com.softbistro.survey.notification.db.entity.NotificationSurveySending;

/**
 * CRUD for entity Client
 * 
 * @author cortes, alex_alokhin
 *
 */
@Repository
public class ClientDao implements IClient {

	private static final Logger LOGGER = Logger.getLogger(ClientDao.class);

	@Value("${count.of.records}")
	private int countOfRecords;
	private static final String SQL_UPDATE_LIST_ID_NEW_SURVEYS = "UPDATE `survey` SET `status`= 'DONE' WHERE status = 'NEW' LIMIT ?";
	private static final String SQL_GET_INFO_OF_USERS_IN_SURVEY = "SELECT   p.client_id, s.id, p.email FROM participant AS p "
			+ "INNER JOIN survey AS s ON p.client_id = s.client_id " + "WHERE  s.status = 'NEW' GROUP BY email";
	private static final String SQL_GET_INFO_OF_NEW_CLIENTS = "SELECT id,email FROM clients "
			+ "WHERE clients.status='NEW'  LIMIT ? ";
	private static final String SQL_GET_INFO_UPDATE_PASSWORD = "SELECT id,email FROM clients "
			+ "WHERE clients.status='VERIFY_PASSWORD'  LIMIT ? ";
	private static final String SQL_UPDATE_STATUS_CLIENTS = "UPDATE clients SET status='IN_PROGRESS' WHERE status = ? LIMIT ?";
	private static final String SELECT_CLIENT_FIRST_PART = "SELECT * FROM clients  WHERE clients.";
	private static final String SELECT_CLIENT_SECOND_PART = " = ? AND clients.`delete` = 0 AND clients.status='DONE'";
	private static final String FIND_CLIENT_BY_ID = "SELECT * FROM clients  WHERE clients.id = ? and clients.`delete` = 0";
	private static final String FIND_CLIENT = "SELECT * FROM clients  WHERE clients.email = ? or clients.client_name = ? and clients.`delete` = 0";
	private static final String SAVE_CLIENT = "INSERT INTO clients (client_name, password, email,status) VALUES(?, ?, ?,'NEW')";
	private static final String SAVE_FACEBOOK_CLIENT = "INSERT INTO clients (client_name, facebook_id, email,status) VALUES(?, ?, ?,'DONE')";
	private static final String SAVE_GOOGLE_CLIENT = "INSERT INTO clients (client_name, google_id, email,status) VALUES(?, ?, ?,'DONE')";
	private static final String UPDATE_CLIENT = "UPDATE clients SET client_name = ?, email = ?, password = ?, facebook_id = ?, google_id = ? WHERE id = ?";
	private static final String DELETE_CLIENT = "UPDATE clients as sc SET sc.`delete` = '1' WHERE sc.id = ?";
	private static final String UPDATE_CLIENT_PASSWORD = "UPDATE clients SET password = ?, status = 'VERIFY_PASSWORD' WHERE id = ?";
	private static final String FACEBOOK = "facebook";
	private static final String GOOGLE = "google";
	private static final String ADD_SOC_INFO = "Add social info answer --- Info add successful";

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private FindClientService findClientService;

	/**
	 * Find client in database by id of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	@Override
	public Client findClient(Integer id) {

		try {

			Client findClient = jdbc.queryForObject(FIND_CLIENT_BY_ID, new BeanPropertyRowMapper<Client>(Client.class),
					id);
			return Optional.ofNullable(findClient).map(client -> client).orElse(null);

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
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
	public Integer saveClient(Client client) {

		try {

			if (findClientByLoginAndEmail(client) != null) {

				return null;
			}

			KeyHolder holder = new GeneratedKeyHolder();

			jdbc.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement preparedStatement = connection.prepareStatement(SAVE_CLIENT,
							Statement.RETURN_GENERATED_KEYS);

					preparedStatement.setString(1, client.getClientName());
					preparedStatement.setString(2, DigestUtils.md5Hex(client.getPassword()));
					preparedStatement.setString(3, client.getEmail());

					return preparedStatement;
				}
			}, holder);

			return holder.getKey().intValue();

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
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
	public Client saveSocialClient(Client client) {

		try {

			Client resultFindClient = findClientService.findClient(client);
			if (resultFindClient == null) {
				return socialSaveClientNotExist(client);
			} else {
				return socialSaveClientExist(client, resultFindClient);
			}
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
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
	public void deleteClient(Integer id) {

		try {

			jdbc.update(DELETE_CLIENT, id);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
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
	public void updateClient(Client client, Integer id) {

		try {

			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(UPDATE_CLIENT, client.getClientName(), client.getEmail(), md5HexPassword,
					client.getFacebookId(), client.getGoogleId(), id);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}

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
	public void updatePassword(Client client, Integer id) {

		try {

			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());
			jdbc.update(UPDATE_CLIENT_PASSWORD, md5HexPassword, id);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}

	}

	/**
	 * Find client by email and client name
	 * 
	 * @param client
	 * @return
	 */
	@Override
	public Client findClientByLoginAndEmail(Client client) {

		try {

			List<Client> clientList = jdbc.query(FIND_CLIENT, new BeanPropertyRowMapper<>(Client.class),
					client.getEmail(), client.getClientName());

			return clientList.stream().findFirst().orElse(null);

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Update some fields in client when client authorized with help of social
	 * networks
	 * 
	 * @param client
	 * @return
	 */
	private Client socialSaveClientNotExist(Client client) {

		try {

			Client resultFindClient = findClientService.findByEmail(client);
			if (resultFindClient != null) {
				return null;
			}
			if (client.getFlag().equals(FACEBOOK)) {

				jdbc.update(SAVE_FACEBOOK_CLIENT, client.getClientName(), client.getFacebookId(), client.getEmail());
			}
			if (client.getFlag().equals(GOOGLE)) {

				jdbc.update(SAVE_GOOGLE_CLIENT, client.getClientName(), client.getGoogleId(), client.getEmail());
			}
			return client;

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
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
	private Client socialSaveClientExist(Client client, Client resultFindClient) {

		try {

			if (client.getFlag().equals(FACEBOOK) && StringUtils.isNotBlank(resultFindClient.getFacebookId())
					&& !resultFindClient.getEmail().equals(client.getEmail())) {

				resultFindClient.setEmail(client.getEmail());
				jdbc.update(UPDATE_CLIENT, resultFindClient.getClientName(), resultFindClient.getEmail(),
						resultFindClient.getPassword(), resultFindClient.getFacebookId(),
						resultFindClient.getGoogleId(), resultFindClient.getId());
				return client;
			}

			if (client.getFlag().equals(GOOGLE) && StringUtils.isNotBlank(resultFindClient.getGoogleId())
					&& !resultFindClient.getEmail().equals(client.getEmail())) {

				resultFindClient.setEmail(client.getEmail());
				jdbc.update(UPDATE_CLIENT, resultFindClient.getClientName(), resultFindClient.getEmail(),
						resultFindClient.getPassword(), resultFindClient.getFacebookId(),
						resultFindClient.getGoogleId(), resultFindClient.getId());
				return client;
			}

			return client;

		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
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
	public Client findByTemplate(String template, String value) {

		try {

			List<Client> clientList = jdbc.query(SELECT_CLIENT_FIRST_PART + template + SELECT_CLIENT_SECOND_PART,
					new BeanPropertyRowMapper<Client>(Client.class), value);

			return clientList.isEmpty() ? null : clientList.get(0);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method that add social data from social networks to exist client
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public void addSocialInfo(Client socialClient) {

		Client updateClient = findClient(socialClient.getId());

		if (StringUtils.isNotBlank(socialClient.getFacebookId()) && StringUtils.isBlank(socialClient.getGoogleId())) {

			updateClient.setFacebookId(socialClient.getFacebookId());
			updateClient(updateClient, updateClient.getId());
			LOGGER.info(ADD_SOC_INFO);
			return;
		}

		if (StringUtils.isNotBlank(socialClient.getGoogleId()) && StringUtils.isBlank(socialClient.getFacebookId())) {

			updateClient.setGoogleId(socialClient.getGoogleId());
			updateClient(updateClient, updateClient.getId());
			LOGGER.info(ADD_SOC_INFO);
		}
	}

	/**
	 * Get mails of clients that change password
	 * 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public List<ClientForSending> getClientUpdatePassword() {
		return jdbc.query(SQL_GET_INFO_UPDATE_PASSWORD, (rs, rowNum) -> {
			return new ClientForSending(rs.getInt(1), rs.getString(2));
		}, countOfRecords);

	}

	/**
	 * Get clients that have registration process
	 * 
	 * @return - list of clients
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public List<ClientForSending> getNewClients() {
		return jdbc.query(SQL_GET_INFO_OF_NEW_CLIENTS, (rs, rowNum) -> {
			return new ClientForSending(rs.getInt(1), rs.getString(2));
		}, countOfRecords);

	}

	/**
	 * Update status of new client
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public void updateStatusOfNewClients() {
		jdbc.update(SQL_UPDATE_STATUS_CLIENTS, "NEW", countOfRecords);
	}

	/**
	 * Update status of clients that update password
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public void updateStatusOfUpdatePassword() {
		jdbc.update(SQL_UPDATE_STATUS_CLIENTS, "VERIFY_PASSWORD", countOfRecords);
	}

	/**
	 * Get clients that started the survey
	 * 
	 * @return - list of clients
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public List<NotificationSurveySending> getClientsForSendingSurvey() {

		return jdbc.query(SQL_GET_INFO_OF_USERS_IN_SURVEY, (rs, rowNum) -> {
			return new NotificationSurveySending(rs.getInt(1), rs.getInt(2), rs.getString(3));
		});

	}

	/**
	 * Update status of survey
	 * 
	 * @author alex_alokhin
	 */
	@Override
	public void updateStatusOfSurvey() {
		jdbc.update(SQL_UPDATE_LIST_ID_NEW_SURVEYS, countOfRecords);
	}

}
