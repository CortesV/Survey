package com.survey.softbistro.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.survey.softbistro.components.entity.Client;
import com.survey.softbistro.service.ClientService;

/**
 * Controller for CRUD of Client
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/client")
public class ClientController {

	@Autowired
	ClientService clientService;

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	@RequestMapping(value = "/getClient", method = RequestMethod.GET)
	public Client findClientByEmail(@RequestHeader String email) {

		return clientService.findClientByEmail(email);
	}

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
	public Map<String, String> saveClient(@RequestBody Client client) {

		return clientService.saveClient(client);
	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "/deleteClient", method = RequestMethod.DELETE)
	public Map<String, String> deleteClient(@RequestHeader String email) {

		return clientService.deleteClient(email);
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
	@RequestMapping(value = "/updateClient", method = RequestMethod.PUT)
	public Map<String, String> updateClient(@RequestBody Client client, @RequestHeader String oldEmail,
			@RequestHeader String oldPassword) {

		return clientService.updateClient(client, oldEmail, oldPassword);
	}

}
