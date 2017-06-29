package com.softbistro.survey.client.auth.service;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;
import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.service.ClientService;
import com.softbistro.survey.client.manage.service.FindClientService;

/**
 * Service for authorization of client
 * 
 * @author cortes
 *
 */
@Service
public class AuthorizationService {

	private static final Logger LOGGER = Logger.getLogger(AuthorizationService.class);
	private static final String FACEBOOK = "facebook";
	private static final String GOOGLE = "google";

	private static final String WRONG_PASS_OR_CLIENT_EXIST = "Simple auth answer --- Wrong password or this client doesn't exist in database";
	private static final String SIMPLE_AUTH = "Simple auth answer --- client was authorized";
	private static final String SOCIAL_AUTH = "Social auth answer --- ";
	private static final String SIMPLE_AUTH_EXCEPTION = "Simple auth exception --- ";
	private static final String SOCIAL_AUTH_EXCEPTION = "Social auth exception --- ";
	private static final String BAD_FLAG = "Bad flag";

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
	public Client simpleAthorization(Client requestClient) {

		Optional<Client> resultFindClient = Optional.ofNullable(findClientService.findByEmail(requestClient));
		return resultFindClient
				.filter(client -> client.getPassword().equals(DigestUtils.md5Hex(requestClient.getPassword())))
				.map(client -> getInformationAfterSimpleAuthotization(client)).orElse(logWrongSimpleAuth());

	}

	private Client getInformationAfterSimpleAuthotization(Client databaseClient) {

		String token = UUID.randomUUID().toString();
		authorizedClientService
				.saveClient(new AuthorizedClient(token, databaseClient.getId().toString(), timeValidKey));
		LOGGER.info(SIMPLE_AUTH);
		return new Client(databaseClient.getId(), databaseClient.getClientName(), databaseClient.getEmail(), token);
	}

	private Client logWrongSimpleAuth() {

		LOGGER.info(WRONG_PASS_OR_CLIENT_EXIST);
		return null;
	}

	/**
	 * Method that do authorization with help of information from social
	 * networks
	 * 
	 * @param client
	 *            client - information about client
	 * @return return - information about client that is authorized
	 */
	public Client socialAuthorization(Client requestClient) {

		if (!checkFlag(requestClient)) {

			LOGGER.info(SOCIAL_AUTH + BAD_FLAG);
			return null;
		}

		clientService.saveSocialClient(requestClient);

		Optional<Client> resultFindClient = Optional.ofNullable(findClientService.findClient(requestClient));

		return resultFindClient.map(client -> getInformationAfterSocialAuthotization(client)).orElse(null);
	}

	private Client getInformationAfterSocialAuthotization(Client databaseClient) {

		String uniqueKey = UUID.randomUUID().toString();
		AuthorizedClient authorizedClient = new AuthorizedClient(uniqueKey, databaseClient.getId().toString(),
				timeValidKey);
		authorizedClientService.saveClient(authorizedClient);

		Client responseClient = new Client(databaseClient.getId(), databaseClient.getClientName(),
				databaseClient.getEmail(), authorizedClient.getToken());

		LOGGER.info(SOCIAL_AUTH + responseClient.toString());
		return responseClient;
	}

	/**
	 * Method that check client's token and if it is valid, time of life token
	 * will continue
	 * 
	 * @param token
	 *            token - token that identify some client
	 */
	public boolean checkAccess(String token) {

		Optional<AuthorizedClient> authorizedClient = Optional.ofNullable(authorizedClientService.findClient(token));
		return authorizedClient.map(client -> {

			client.setTimeValidKey(client.getTimeValidKey());
			authorizedClientService.saveClient(client);
			return true;
		}).orElse(false);
	}

	/**
	 * Method for checking exist this token in redis or not
	 * 
	 * @param token
	 * @return
	 */
	public boolean checkToken(String token) {

		return !checkAccess(token) ? false : true;
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

}
