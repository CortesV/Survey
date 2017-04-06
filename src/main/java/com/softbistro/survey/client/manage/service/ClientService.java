package com.softbistro.survey.client.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.components.interfaces.IClient;

/**
 * Service for CRUD of Client
 * 
 * @author cortes
 *
 */
@Service
public class ClientService {

	@Autowired
	private IClient iClient;

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	public ResponseEntity<Client> findClientByEmail(String email) {

		return iClient.findClientByEmail(email);
	}

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Object> saveClient(Client client) {

		return iClient.saveClient(client);
	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Object> deleteClient(Integer id) {

		return iClient.deleteClient(id);

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
	public ResponseEntity<Object> updateClient(Client client, Integer id) {

		return iClient.updateClient(client, id);
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
	public ResponseEntity<Object> updatePassword(Client client, Integer id) {

		return iClient.updatePassword(client, id);
	}

	public ResponseEntity<Object> saveSocialClient(Client client) {

		return iClient.saveSocialClient(client);
	}

}
