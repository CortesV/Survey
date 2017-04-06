package com.softbistro.survey.client.manage.components.interfaces;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.client.manage.components.entity.Client;

public interface IClient {

	/**
	 * Find client in database by id of client
	 * 
	 * @param id
	 *            id - id of client
	 * @return return - client's information
	 */

	public ResponseEntity<Client> findClient(Integer id);

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - information about of client
	 */
	public ResponseEntity<Object> saveClient(Client client);

	/**
	 * Delete client from database by email of client
	 * 
	 * @param id
	 *            id - id of client
	 * @return return - information about of client
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
	 * @return return - information about of client
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
	 * @return return - information about of client
	 */
	public ResponseEntity<Object> updatePassword(Client client, Integer id);

	/**
	 * Save information about client that authorized with help of social
	 * networks
	 * 
	 * @param client
	 * @return
	 */
	public ResponseEntity<Client> saveSocialClient(Client client);
	

	/**
	 * Find client by email and client name
	 * 
	 * @param client
	 * @return
	 */
	public ResponseEntity<Client> findClientByLoginAndEmail(Client client);

	/**
	 * Find client by email, facebookId or googleId
	 * 
	 * @param template
	 *            template - email, facebookId or googleId
	 * @param value
	 *            value - value of template
	 * @return return - information about of client
	 */
	public ResponseEntity<Client> findByTemplate(String template, String value);

}
