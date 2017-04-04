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
	public Response findClient(Integer id);

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - information about of client
	 */
	public Response saveClient(Client client);

	/**
	 * Delete client from database by email of client
	 * 
	 * @param id
	 *            id - id of client
	 * @return return - information about of client
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
	 * @return return - information about of client
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
	 * @return return - information about of client
	 */
	public Response updatePassword(Client client, Integer id);

	/**
	 * Save information about client that authorized with help of social
	 * networks
	 * 
	 * @param client
	 * @return
	 */
	public Response saveSocialClient(Client client);

	/**
	 * Find client by email and client name
	 * 
	 * @param client
	 * @return
	 */
	public Response findClientByLoginAndEmail(Client client);

	/**
	 * Find client by email, facebookId or googleId
	 * 
	 * @param template
	 *            template - email, facebookId or googleId
	 * @param value
	 *            value - value of template
	 * @return return - information about of client
	 */
	public Response findByTemplate(String template, String value);
}
