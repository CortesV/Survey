package com.softbistro.survey.client.manage.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.service.ClientService;
import com.softbistro.survey.notification.db.service.ChangePasswordMessageService;
import com.softbistro.survey.notification.db.service.RegistrationMessageServise;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for CRUD of Client
 * 
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/rest/survey/v1/client")
public class ClientController {

	private static final Logger LOGGER = Logger.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ChangePasswordMessageService changePassService;

	@Autowired
	private RegistrationMessageServise registrationService;

	/**
	 * Find client in database by id of client
	 * 
	 * @param email
	 *            id - id of client
	 * @return return - client's information
	 */

	@ApiOperation(value = "Get Client By id", notes = "Get Client instanse by client id", tags = "Client")
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Client> findClient(@PathVariable("id") Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(clientService.findClient(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Save client to database
	 * 
	 * @param client
	 *            client - all information about client that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	@ApiOperation(value = "Create new Client", notes = "Create new Client instanse by client name, password, email", tags = "Client")
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> saveClient(@RequestBody Client client) {

		try {

			Integer id = clientService.saveClient(client);
			if (id != null) {
				registrationService.send();
				return new ResponseEntity<>(id, HttpStatus.CREATED);

			} else {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete client from database by email of client
	 * 
	 * @param email
	 *            email - email of client
	 * @return return - status of execution this method
	 */
	@ApiOperation(value = "Delete Client By id", notes = "Delete Client instanse by client id", tags = "Client")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Client> deleteClient(@PathVariable("id") Integer id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			clientService.deleteClient(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	@ApiOperation(value = "Update Client By Id", notes = "Update Client instanse by client name, password, email and client id", tags = "Client")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Client> updateClient(@RequestBody Client client, @PathVariable("id") Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			clientService.updateClient(client, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	@ApiOperation(value = "Update Client Password By Id", notes = "Update Client password by password and client id", tags = "Client")
	@RequestMapping(value = "/password/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Client> updatePassword(@RequestBody Client client, @PathVariable("id") Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {

			clientService.updatePassword(client, id);
			changePassService.send();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
