package com.softbistro.survey.client.auth.service;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;
import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.service.ClientService;

/**
 * Service for authorization of client
 * 
 * @author cortes
 *
 */
@Service
public class AuthorizationService {

	@Value("${redis.life.token}")
	private Integer timeValidKey;

	@Autowired
	private AuthorizedClientService authorizedClientService;

	@Autowired
	private ClientService clientService;

	/**
	 * Method that do simple authorization of client
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Client> simpleAthorization(Client client) {

		AuthorizedClient authorizedClient;
		Client responseClient;
		try {

			Client resultFindClient = (Client) clientService.findClientByEmail(client.getEmail()).getBody();
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());

			if (resultFindClient == null || !resultFindClient.getPassword().equals(md5HexPassword)) {

				return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
			}

			String uniqueKey = UUID.randomUUID().toString();
			authorizedClient = new AuthorizedClient(uniqueKey, resultFindClient.getId().toString(), timeValidKey);
			authorizedClientService.saveClient(authorizedClient);
			responseClient = new Client();
			responseClient.setId(resultFindClient.getId());
			responseClient.setClientName(resultFindClient.getClientName());
			responseClient.setEmail(resultFindClient.getEmail());
			responseClient.setToken(authorizedClient.getUniqueKey());

		} catch (Exception ex) {

			return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Client>(responseClient, HttpStatus.OK);
	}

	/**
	 * Method that do authorization with help of information from social
	 * networks
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - status of execution this method
	 */
	public ResponseEntity<Client> socialAuthorization(Client client) {

		AuthorizedClient authorizedClient;
		Client responseClient;
		try {

			clientService.saveSocialClient(client);

			Client resultFindClient = (Client) clientService.findClientByEmail(client.getEmail()).getBody();

			if (resultFindClient == null) {

				return new ResponseEntity<Client>(HttpStatus.OK);
			}

			if (resultFindClient.getFacebookId() != null
					&& !resultFindClient.getFacebookId().equals(client.getFacebookId())
					&& StringUtils.isNotBlank(client.getFacebookId())) {

				return new ResponseEntity<Client>(HttpStatus.OK);
			}

			if (resultFindClient.getGoogleId() != null && !resultFindClient.getGoogleId().equals(client.getGoogleId())
					&& StringUtils.isNotBlank(client.getGoogleId())) {

				return new ResponseEntity<Client>(HttpStatus.OK);
			}

			String uniqueKey = UUID.randomUUID().toString();
			authorizedClient = new AuthorizedClient(uniqueKey, resultFindClient.getId().toString(), timeValidKey);
			authorizedClientService.saveClient(authorizedClient);
			responseClient = new Client();
			responseClient.setId(resultFindClient.getId());
			responseClient.setClientName(resultFindClient.getClientName());
			responseClient.setEmail(resultFindClient.getEmail());
			responseClient.setToken(authorizedClient.getUniqueKey());

		} catch (Exception ex) {

			return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Client>(responseClient, HttpStatus.OK);
	}

	/**
	 * Method that check client's token and if it is valid, time of life token
	 * will continue
	 * 
	 * @param token
	 *            token - token that identify some client
	 */
	public boolean checkAccess(String token) {

		AuthorizedClient authorizedClient = authorizedClientService.findClient(token);

		if (authorizedClient == null) {

			return false;
		}

		authorizedClient.setTimeValidKey(authorizedClient.getTimeValidKey());
		authorizedClientService.saveClient(authorizedClient);
		return true;
	}

	/**
	 * Method for checking exist this token in redis or not
	 * 
	 * @param token
	 * @return
	 */
	public ResponseEntity<Object> checkToken(String token) {

		if (!checkAccess(token)) {

			return new ResponseEntity<Object>(false, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Object>(true, HttpStatus.OK);
	}

}
