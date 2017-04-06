package com.softbistro.survey.client.auth.controller;

import org.apache.log4j.Logger;
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
	
	private static final Logger LOGGER = Logger.getLogger(AuthController.class);
	private static final String SIMPLE_AUTH = "Simple auth request --- ";
	private static final String SOCIAL_AUTH = "Social auth request --- ";
	private static final String LOGOUT = "Logout request --- ";
	private static final String LOGOUT_FAIL = "Logout request --- Unauthorized client";
	private static final String ADD_SOC_INFO = "Add social info request --- ";
	private static final String ADD_SOC_FAIL = "Add social info request --- Unauthorized client";

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

		LOGGER.info(SIMPLE_AUTH + client.toString());
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

		LOGGER.info(SOCIAL_AUTH + client.toString());
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

		LOGGER.info(LOGOUT + token);
		
		if (!authorizationService.checkAccess(token)) {

			LOGGER.info(LOGOUT_FAIL);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		
		authorizedClientService.deleteClients(token);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Controller that add social data from social networks to exist client
	 * 
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "Add social info", notes = "Add social information for authorized client", tags = "Authorization")
	@RequestMapping(value = "/add/social", method = RequestMethod.POST)
	public ResponseEntity<Client> setClient1(@RequestHeader String token, @RequestBody Client client) {

		LOGGER.info(ADD_SOC_INFO + client.toString() + "   " +  token);
		if (!authorizationService.checkAccess(token)) {

			LOGGER.info(ADD_SOC_FAIL);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return authorizationService.addSocialInfo(client);
	}

}
