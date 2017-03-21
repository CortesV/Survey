package com.softbistro.survey.client.auth.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;
import com.softbistro.survey.client.auth.components.interfaces.IAuthorizedClientRepository;

@Service
public class AuthorizedClientService {

	@Autowired
	IAuthorizedClientRepository iAuthorizedClientRepository;

	/**
	 * Method that save info about authorized client
	 * 
	 * @param client
	 *            client - info about authorized client such as clientId,
	 *            uniqueKey, timeValidKey
	 */
	public void saveClient(AuthorizedClient client) {

		iAuthorizedClientRepository.saveClient(client);
	}

	/**
	 * Method that update info about authorized client
	 * 
	 * @param client
	 *            client - info about authorized client such as clientId,
	 *            uniqueKey, timeValidKey
	 */
	public void updateClient(AuthorizedClient client) {

		iAuthorizedClientRepository.updateClient(client);
	}

	/**
	 * Method that find record about authorized client in cash
	 * 
	 * @param id
	 *            id - key that identify some record in redis
	 * @return return - information about client in redis
	 */
	public AuthorizedClient findClient(String id) {

		return iAuthorizedClientRepository.findClient(id);
	}

	/**
	 * Method that return all records in cash
	 * 
	 * @return
	 */
	public Map<String, AuthorizedClient> findAllClients() {

		return iAuthorizedClientRepository.findAllClients();
	}

	/**
	 * Method that delete record in cash by key id
	 * 
	 * @param id
	 */
	public void deleteClients(String id) {

		iAuthorizedClientRepository.deleteClients(id);
	}
}
