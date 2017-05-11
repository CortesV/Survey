package com.softbistro.survey.client.manage.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.client.manage.components.entity.Client;

/**
 * Service that find client in database by templates
 * 
 * @author cortes
 *
 */
@Service
public class FindClientService {

	private static final String EMAIL = "email";
	private static final String FACEBOOK_ID = "facebook_id";
	private static final String GOOGLE_ID = "google_id";

	@Autowired
	private ClientService clientService;

	/**
	 * Method that find client in database by templates
	 * 
	 * @param client
	 *            client - information that get by http request
	 * @return return - information about client from database
	 */
	public Client findClient(Client client) {

		String credential = null;
		String template = null;

		if (StringUtils.isNotBlank(client.getFacebookId())) {

			credential = client.getFacebookId();
			template = FACEBOOK_ID;
		} else if (StringUtils.isNotBlank(client.getGoogleId())) {
			
			credential = client.getGoogleId();
			template = GOOGLE_ID;
		}

		return clientService.findByTemplate(template, credential);

	}

	/**
	 * Method that find client by email
	 * @param client
	 * @return
	 */
	public Client findByEmail(Client client) {

		String credential = null;
		String template = null;

		if (StringUtils.isNotBlank(client.getEmail())) {

			credential = client.getEmail();
			template = EMAIL;
		}

		return clientService.findByTemplate(template, credential);
	}
}
