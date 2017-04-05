package com.softbistro.survey.client.manage.components.interfaces;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.client.manage.components.entity.Client;

public interface IClient {

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	public ResponseEntity<Client> findClientByEmail(String email);

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Object> saveClient(Client client);

	/**
	 * Delete client from database by email of client
	 * 
	 * @param id
	 *            id - id of client
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Object> deleteClient(Integer id);

	/**
	 * Update information of client
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @param id
	 *            id - id of client
	 * 
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Object> updateClient(Client client, Integer id);

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
	public ResponseEntity<Object> updatePassword(Client client, Integer id);

	/**
	 * Save information about client that authorized with help of social
	 * networks
	 * 
	 * @param client
	 * @return
	 */
	public ResponseEntity<Object> saveSocialClient(Client client);
	
	/**
	 * Find client by email and client name
	 * 
	 * @param client
	 * @return
	 */
	public ResponseEntity<Client> findClientByLoginAndEmail(Client client);
}
