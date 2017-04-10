package com.softbistro.survey.client.auth.service;

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
	private static final String SIMPLE_AUTH = "Simple auth answer --- ";
	private static final String SOCIAL_AUTH = "Social auth answer --- ";
	private static final String SIMPLE_AUTH_EXCEPTION = "Simple auth exception --- ";
	private static final String SOCIAL_AUTH_EXCEPTION = "Social auth exception --- ";
	private static final String BAD_FLAG = "Bad flag";
	private static final String NOT_EXIST_IN_DB = "Can not find client in database";
	private static final String WRONG_FACEBOOK = "Something wrong with facebook id";
	private static final String WRONG_GOOGLE = "Something wrong with google id";
	private static final String WRONG_EMAIL = "Something wrong with email";
	

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

		AuthorizedClient authorizedClient;
		Client responseClient;
		try {

			Client resultFindClient = findClientService.findByEmail(requestClient);

			String md5HexPassword = DigestUtils.md5Hex(requestClient.getPassword());

			if (resultFindClient == null || !resultFindClient.getPassword().equals(md5HexPassword)) {

				LOGGER.info(WRONG_PASS_OR_CLIENT_EXIST);
				return null;
			}

			String uniqueKey = UUID.randomUUID().toString();
			authorizedClient = new AuthorizedClient(uniqueKey, resultFindClient.getId().toString(), timeValidKey);
			authorizedClientService.saveClient(authorizedClient);

			responseClient = new Client();
			responseClient.setId(resultFindClient.getId());
			responseClient.setClientName(resultFindClient.getClientName());
			responseClient.setEmail(resultFindClient.getEmail());
			responseClient.setToken(authorizedClient.getUniqueKey());

			LOGGER.info(SIMPLE_AUTH + responseClient.toString());
			return responseClient;

		} catch (Exception e) {

			LOGGER.error(SIMPLE_AUTH_EXCEPTION, e);
			return null;
		}

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

		AuthorizedClient authorizedClient;
		Client responseClient;
		try {

			clientService.saveSocialClient(requestClient);

			if (!checkFlag(requestClient)) {

				LOGGER.info(SOCIAL_AUTH + BAD_FLAG);
				return null;
			}

			clientService.saveSocialClient(requestClient);

			Client resultFindClient = findClientService.findClient(requestClient);

			if (resultFindClient == null) {

				LOGGER.info(SOCIAL_AUTH + NOT_EXIST_IN_DB);
				return null;
			}

			if (resultFindClient.getFacebookId() != null
					&& !resultFindClient.getFacebookId().equals(requestClient.getFacebookId())
					&& StringUtils.isNotBlank(requestClient.getFacebookId())) {

				LOGGER.info(SOCIAL_AUTH + WRONG_FACEBOOK);
				return null;
			}

			if (resultFindClient.getGoogleId() != null
					&& !resultFindClient.getGoogleId().equals(requestClient.getGoogleId())
					&& StringUtils.isNotBlank(requestClient.getGoogleId())) {

				LOGGER.info(SOCIAL_AUTH + WRONG_GOOGLE);
				return null;
			}

			if (resultFindClient.getEmail() != null && !resultFindClient.getEmail().equals(requestClient.getEmail())
					&& StringUtils.isNotBlank(requestClient.getEmail())) {

				LOGGER.info(SOCIAL_AUTH + WRONG_EMAIL);
				return null;
			}

			String uniqueKey = UUID.randomUUID().toString();
			authorizedClient = new AuthorizedClient(uniqueKey, resultFindClient.getId().toString(), timeValidKey);
			authorizedClientService.saveClient(authorizedClient);

			responseClient = new Client();
			responseClient.setId(resultFindClient.getId());
			responseClient.setClientName(resultFindClient.getClientName());
			responseClient.setEmail(resultFindClient.getEmail());
			responseClient.setToken(authorizedClient.getUniqueKey());

			LOGGER.info(SOCIAL_AUTH + responseClient.toString());
			return responseClient;

		} catch (Exception e) {

			LOGGER.error(SOCIAL_AUTH_EXCEPTION, e);
			return null;
		}

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
