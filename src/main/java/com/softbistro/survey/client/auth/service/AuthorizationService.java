package com.softbistro.survey.client.auth.service;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";
	private static final String AUTHORIZED_CLIENT = "Client is authorized";

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
		Client responseClient;
		try {

			Client resultFindClient = (Client) clientService.findClientByEmail(client.getEmail()).getData();
			String md5HexPassword = DigestUtils.md5Hex(client.getPassword());

			if (resultFindClient == null || !resultFindClient.getPassword().equals(md5HexPassword)) {

				return new Response(null, HttpStatus.OK, NOT_FOUND_CLIENT);
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

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return new Response(responseClient, HttpStatus.OK, null);
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
		Client responseClient;
		try {

			Response saveResponse = clientService.saveSocialClient(client);

			Client resultFindClient = (Client) clientService.findClientByEmail(client.getEmail()).getData();

			if (resultFindClient == null) {

				return new Response(null, HttpStatus.OK, NOT_FOUND_SOC_CLIENT);
			}

			if (resultFindClient.getFacebookId() != null
					&& !resultFindClient.getFacebookId().equals(client.getFacebookId())
					&& StringUtils.isNotBlank(client.getFacebookId())) {

				return new Response(null, HttpStatus.OK, NOT_FOUND_SOC_CLIENT);
			}

			if (resultFindClient.getGoogleId() != null && !resultFindClient.getGoogleId().equals(client.getGoogleId())
					&& StringUtils.isNotBlank(client.getGoogleId())) {

				return new Response(null, HttpStatus.OK, NOT_FOUND_SOC_CLIENT);
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

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return new Response(responseClient, HttpStatus.OK, null);
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
	public Response checkToken(String token) {

		if (!checkAccess(token)) {

			return new Response(false, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return new Response(true, HttpStatus.OK, AUTHORIZED_CLIENT);
	}

}
