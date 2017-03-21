package com.softbistro.survey.client.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.components.entity.SearchingTemplate;
import com.softbistro.survey.client.manage.service.ClientService;
import com.softbistro.survey.response.Response;

/**
 * Controller for CRUD of Client
 * 
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/rest/survey/v1/client")
public class ClientController {
	
	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";

	@Autowired
	private ClientService clientService;

	@Autowired
	private AuthorizationService authorizationService;

	/**
	 * Find client in database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - client's information
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response findClientByEmail(@RequestBody SearchingTemplate template, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

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
	public Response saveClient(@RequestBody Client client, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return clientService.saveClient(client);
	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteClient(@PathVariable("id") Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return clientService.deleteClient(id);
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
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Response updateClient(@RequestBody Client client, @PathVariable("id") Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return clientService.updateClient(client, id);
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
	@RequestMapping(value = "/password/{id}", method = RequestMethod.PUT)
	public Response updatePassword(@RequestBody Client client, @PathVariable("id") Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return clientService.updatePassword(client, id);
	}

}
