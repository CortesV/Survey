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
import com.softbistro.survey.client.manage.service.FindClientService;
import com.softbistro.survey.response.Response;

/**
 * Service for authorization of client
 * 
 * @author cortes
 *
 */
@Service
public class AuthorizationService {

	private static final String NOT_FOUND_CLIENT = "Wrong password or email";
	private static final String NOT_FOUND_SOC_CLIENT = "Bad credentials";
	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";
	private static final String AUTHORIZED_CLIENT = "Client is authorized";
	private static final String FACEBOOK = "facebook";
	private static final String GOOGLE = "google";

	@Value("${redis.life.token}")
	private Integer timeValidKey;

	@Autowired
	private AuthorizedClientService authorizedClientService;

	@Autowired
	private ClientService clientService;

	@Autowired
	FindClientService findClientService;

	/**
	 * Method that do simple authorization of client
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - information about client that is authorized
	 */
	public Response simpleAthorization(Client client) {

		AuthorizedClient authorizedClient;
		Client responseClient;
		try {

			Client resultFindClient = findClientService.findClient(client);
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

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new Response(responseClient, HttpStatus.OK, null);
	}

	/**
	 * Method that do authorization with help of information from social
	 * networks
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - information about client that is authorized
	 */
	public Response socialAuthorization(Client client) {

		AuthorizedClient authorizedClient;
		Client responseClient;
		try {

			if (!checkFlag(client)) {

				return new Response(null, HttpStatus.OK, NOT_FOUND_SOC_CLIENT);
			}

			clientService.saveSocialClient(client);

			Client resultFindClient = findClientService.findClient(client);

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
			
			if (resultFindClient.getEmail() != null && !resultFindClient.getEmail().equals(client.getEmail())
					&& StringUtils.isNotBlank(client.getEmail())) {

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

		} catch (Exception e) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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

	/**
	 * Method for checking if got flag equals standart flags
	 * 
	 * @param flag
	 * @return
	 */
	private boolean checkFlag(Client client) {

		return StringUtils.isNotBlank(client.getGoogleId()) && client.getFlag().equals(GOOGLE)
				|| StringUtils.isNotBlank(client.getFacebookId()) && client.getFlag().equals(FACEBOOK);
	}

	public Response addSocialInfo(Client socialClient) {

		Client updateClient = (Client) clientService.findClient(socialClient.getId()).getData();
		if (StringUtils.isNotBlank(socialClient.getFacebookId()) && StringUtils.isBlank(socialClient.getGoogleId())) {

			updateClient.setFacebookId(socialClient.getFacebookId());
			clientService.updateClient(updateClient, updateClient.getId());
			return new Response(null, HttpStatus.OK, null);
		}

		if (StringUtils.isNotBlank(socialClient.getGoogleId()) && StringUtils.isBlank(socialClient.getFacebookId())) {

			updateClient.setGoogleId(socialClient.getGoogleId());
			clientService.updateClient(updateClient, updateClient.getId());
			return new Response(null, HttpStatus.OK, null);
		}
		return new Response(null, HttpStatus.OK, NOT_FOUND_SOC_CLIENT);
	}

}
