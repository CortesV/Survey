package com.softbistro.survey.client.auth.service;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;
import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.service.ClientService;
import com.softbistro.survey.response.Response;

/**
 * Service for authorization of client
 * 
 * @author cortes
 *
 */
@Service
public class AuthorizationService {

	private static final String EMPTY_PASSWORD = "";
	private static final String NOT_FOUND_CLIENT = "Wrong password or email";
	private static final String NOT_FOUND_SOC_CLIENT = "Client with entered credentials isn't exist in database";

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
	public Response simpleAthorization(Client client) {

		AuthorizedClient authorizedClient;
		try {

			Client resultFindClient = (Client) clientService.findClientByEmail(client.getEmail()).getData();
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());

			if (resultFindClient.getId() == null && !resultFindClient.getPassword().equals(md5HexPassword)) {

				return new Response(resultFindClient, HttpStatus.OK, NOT_FOUND_CLIENT);
			}

			String uniqueKey = UUID.randomUUID().toString();
			authorizedClient = new AuthorizedClient(uniqueKey, resultFindClient.getId().toString(), timeValidKey);
			authorizedClientService.saveClient(authorizedClient);

		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return new Response(authorizedClient.getUniqueKey(), HttpStatus.OK, null);
	}

	/**
	 * Method that do authorization with help of information from social
	 * networks
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - status of execution this method
	 */
	public Response socialAuthorization(Client client) {

		AuthorizedClient authorizedClient;
		try {

			client.setPassword(EMPTY_PASSWORD);
			Response saveResponse = clientService.saveSocialClient(client);

			Client resultFindClient = (Client) clientService.findClientByEmail(client.getEmail()).getData();

			if (resultFindClient == null) {

				return new Response(null, HttpStatus.OK, NOT_FOUND_SOC_CLIENT);
			}

			if (!resultFindClient.getFacebookId().equals(client.getFacebookId())
					&& !resultFindClient.getGoogleId().equals(client.getGoogleId())) {

				return new Response(null, HttpStatus.OK, NOT_FOUND_SOC_CLIENT);
			}

			String uniqueKey = UUID.randomUUID().toString();
			authorizedClient = new AuthorizedClient(uniqueKey, resultFindClient.getId().toString(), timeValidKey);
			authorizedClientService.saveClient(authorizedClient);

		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return new Response(authorizedClient.getUniqueKey(), HttpStatus.OK, null);
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

}
