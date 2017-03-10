package com.survey.softbistro.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.softbistro.components.entity.Client;
import com.survey.softbistro.components.interfaces.IClient;

/**
 * Service for CRUD of Client
 * @author cortes
 *
 */
@Service
public class ClientService {

	@Autowired
	IClient iClient;

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	public Client findClientByEmail(String email) {
		
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
	public Map<String, String> saveClient(Client client) {
		
		return iClient.saveClient(client);
	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	public Map<String, String> deleteClient(String email) {
		
		return iClient.deleteClient(email);

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
	public Map<String, String> updateClient(Client client, String oldEmail, String oldPassword) {

		return iClient.updateClient(client, oldEmail, oldPassword);
	}

	

	
}
