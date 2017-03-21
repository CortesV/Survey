package com.softbistro.survey.client.manage.components.interfaces;

import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.response.Response;

public interface IClient {

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	public Response findClientByEmail(String email);

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	public Response saveClient(Client client);

	/**
	 * Delete client from database by email of client
	 * 
	 * @param id
	 *            id - id of client
	 * @return return - status of execution this method
	 */
	public Response deleteClient(Integer id);

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
	public Response updateClient(Client client, Integer id);
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
	public Response updatePassword(Client client, Integer id);
}
