package com.softbistro.survey.client.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.client.auth.service.AuthorizedClientService;
import com.softbistro.survey.client.manage.components.entity.Client;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for authorization of Client
 * 
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/rest/survey/v1/")
public class AuthController {

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
	@ApiOperation(value = "Simple Authorization", notes = "Do simple authorization of client by client email and password", tags = "Authorization")
	@RequestMapping(value = "auth/simple", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Client> simpleAuth(@RequestBody Client client) {

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
	@ApiOperation(value = "Social Network Authorization", notes = "do authorization with help of information from social networks", tags = "Authorization")
	@RequestMapping(value = "auth/social", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Client> socialAuth(@RequestBody Client client) {

		return authorizationService.socialAuthorization(client);
	}

	/**
	 * Method that do logout of client
	 * 
	 * @param token
	 *            token - token of some client
	 * @return
	 */
	@ApiOperation(value = "Logout Client", notes = "Logout the client by client token", tags = "Authorization")
	@RequestMapping(value = "logout", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> logout(@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		authorizedClientService.deleteClients(token);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
