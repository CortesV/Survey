package com.survey.softbistro.startapp;

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
import com.softbistro.survey.startapp.SurveySoftBistroApplication;

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
	private AuthorizedClientDao authorizedClientService;

	private AuthorizedClient authorizedClientTest;

	@Before
	public void setUp() {

		authorizedClientTest = new AuthorizedClient();
		authorizedClientTest.setUniqueKey("c428d4bc-fde5-4eef-b4cb-b8f48a72ffb2");
		authorizedClientTest.setClientId("1");
		authorizedClientTest.setTimeValidKey(10);
	}

	/**
	 * Test of save authorized client to redis
	 */
	@Test
	public void saveAuthorizedClientTest() {

		authorizedClientService.saveClient(authorizedClientTest);
		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey(),
				authorizedClientTest.getUniqueKey());
	}

	/**
	 * Test of update authorized client
	 */
	@Test
	public void updateAuthorizedClientTest() {

		authorizedClientService.saveClient(authorizedClientTest);
		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey(),
				authorizedClientTest.getUniqueKey());

		authorizedClientTest.setClientId("2");

		authorizedClientService.updateClient(authorizedClientTest);

		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getClientId(),
				authorizedClientTest.getClientId());
	}

	/**
	 * Test of delete authorized client
	 */
	@Test
	public void deleteAuthorizedClientTest() {

		authorizedClientService.saveClient(authorizedClientTest);
		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey(),
				authorizedClientTest.getUniqueKey());

		authorizedClientService
				.deleteClients(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey());
	}

	/**
	 * Test of find all authorized clients in redis
	 */
	@Test
	public void findAllAuthorizedClientTest() {

		int size = authorizedClientService.findAllClients().size();
		assertEquals(authorizedClientService.findAllClients().size(), size);
	}
}
