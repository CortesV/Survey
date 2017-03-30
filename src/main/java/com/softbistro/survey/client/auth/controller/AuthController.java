package com.softbistro.survey.client.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.client.auth.service.AuthorizedClientService;
import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.response.Response;

/**
 * Controller for authorization of Client
 * 
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/rest/survey/v1/")
public class AuthController {

	private static final String LOGOUT_SUCCESSFUL = "Logout is successful";
	
	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private AuthorizedClientService authorizedClientService;

	/**
	 * Method that do simple authorization of client
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "auth/simple", method = RequestMethod.POST)
	public Response simpleAuth(@RequestBody Client client) {

		return authorizationService.simpleAthorization(client);
	}

	/**
	 * Method that do authorization with help of information from social
	 * networks
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "auth/social", method = RequestMethod.POST)
	public Response socialAuth(@RequestBody Client client) {

		return authorizationService.socialAuthorization(client);
	}

	/**
	 * Method that do logout of client
	 * 
	 * @param token
	 *            token - token of some client
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public Response logout(@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, "Unauthorized client");
		}
		authorizedClientService.deleteClients(token);

		return new Response(null, HttpStatus.OK, LOGOUT_SUCCESSFUL);
	}

	@RequestMapping(value = "/cash2", method = RequestMethod.GET)
	public Response setClient1(@RequestHeader String token) {

		return new Response(authorizedClientService.findClient(token), HttpStatus.OK, "OK");
	}

}
