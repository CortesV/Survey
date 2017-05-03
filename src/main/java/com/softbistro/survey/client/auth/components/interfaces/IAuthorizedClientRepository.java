package com.softbistro.survey.client.auth.components.interfaces;

import java.util.Map;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;

public interface IAuthorizedClientRepository {

	/**
	 * Method that save info about authorized client
	 * 
	 * @param client
	 *            client - info about authorized client such as clientId,
	 *            uniqueKey, timeValidKey
	 */
	public void saveClient(AuthorizedClient client);

	/**
	 * Method that update info about authorized client
	 * 
	 * @param client
	 *            client - info about authorized client such as clientId,
	 *            uniqueKey, timeValidKey
	 */
	public void updateClient(AuthorizedClient client);

	/**
	 * Method that find record about authorized client in cash
	 * 
	 * @param id
	 *            id - key that identify some record in redis
	 * @return return - information about client in redis
	 */
	public AuthorizedClient findClient(String id);

	/**
	 * Method that return all records in cash
	 * 
	 * @return
	 */
	public Map<String, AuthorizedClient> findAllClients();

	/**
	 * Method that delete record in cash by key id
	 * 
	 * @param id
	 */
	public void deleteClients(String id);
}
