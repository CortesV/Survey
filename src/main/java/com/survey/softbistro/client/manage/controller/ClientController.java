package com.survey.softbistro.client.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.survey.softbistro.client.manage.components.entity.Client;
import com.survey.softbistro.client.manage.components.entity.SearchingTemplate;
import com.survey.softbistro.client.manage.service.ClientService;
import com.survey.softbistro.response.Response;

/**
 * Controller for CRUD of Client
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/rest/survey/v1/client")
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
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response findClientByEmail(@RequestBody SearchingTemplate template) {

		return clientService.findClientByEmail(template.getEmail());
	}

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Response saveClient(@RequestBody Client client) {

		return clientService.saveClient(client);
	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public Response deleteClient(@RequestHeader String email) {

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
	public Response updateClient(@RequestBody Client client, @RequestHeader String oldEmail,
			@RequestHeader String oldPassword) {

		return clientService.updateClient(client, oldEmail, oldPassword);
	}

}
