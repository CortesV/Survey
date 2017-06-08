package com.softbistro.survey.client.auth.component.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;
import com.softbistro.survey.client.auth.components.service.AuthorizedClientDao;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for authorized client dao
 * 
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class AuthorizedClientIntegrationTest {

	@Autowired
	private AuthorizedClientDao authorizedClientDao;

	private AuthorizedClient authorizedClientTest;

	private static final String CLIENT_ID = "1";
	private static final String CLIENT_TOKEN = "c428d4bc-fde5-4eef-b4cb-b8f48a72ffb2";

	@Before
	public void setUp() {

		authorizedClientTest = new AuthorizedClient();
		authorizedClientTest.setToken(CLIENT_TOKEN);
		authorizedClientTest.setClientId(CLIENT_ID);
		authorizedClientTest.setTimeValidKey(10);

		authorizedClientDao.saveClient(authorizedClientTest);
	}

	/**
	 * Test of save authorized client to redis
	 */
	@Test
	public void saveAuthorizedClientTest() {

		assertEquals(authorizedClientDao.findClient(CLIENT_TOKEN).getClientId(), CLIENT_ID);
	}

	/**
	 * Test of update authorized client
	 */
	@Test
	public void updateAuthorizedClientTest() {
		String clientUpdateId = "2";

		authorizedClientTest.setClientId(clientUpdateId);

		authorizedClientDao.updateClient(authorizedClientTest);

		assertEquals(authorizedClientDao.findClient(CLIENT_TOKEN).getClientId(), clientUpdateId);
	}

	/**
	 * Test of delete authorized client
	 */
	@Test
	public void deleteAuthorizedClientTest() {

		authorizedClientDao.deleteClients(authorizedClientDao.findClient(CLIENT_TOKEN).getToken());
	}

	/**
	 * Test of find all authorized clients in redis
	 */
	@Test
	public void findAllAuthorizedClientTest() {

		int size = authorizedClientDao.findAllClients().size();
		assertEquals(authorizedClientDao.findAllClients().size(), size);
	}
}
