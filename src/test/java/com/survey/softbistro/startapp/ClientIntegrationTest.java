package com.survey.softbistro.startapp;

import static org.junit.Assert.assertEquals;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.client.manage.components.entity.Client;
import com.softbistro.survey.client.manage.components.service.ClientDao;
import com.softbistro.survey.client.manage.service.FindClientService;
import com.softbistro.survey.startapp.SurveySoftBistroApplication;

/**
 * Integration test for Client dao
 * 
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class ClientIntegrationTest {

	@Autowired
	private ClientDao clientService;

	@Autowired
	private FindClientService findClientService;

	private Client testClient;

	@Before
	public void setUp() {

		testClient = new Client();
		testClient.setClientName("SurveyManager");
		testClient.setEmail("SurveyManager@gmail.com");
		testClient.setPassword("SurveyManager");
	}

	/**
	 * Method that test save client to database
	 */
	@Test
	public void saveClientTest() {

		clientService.saveClient(testClient);
		Client findServiceClient = findClientService.findByEmail(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getId(), findServiceClient.getId());
	}

	/**
	 * Method that test get client by id
	 */
	@Test
	public void getClientTest() {

		clientService.saveClient(testClient);
		Client findServiceClient = findClientService.findByEmail(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getEmail(), testClient.getEmail());
	}

	/**
	 * Test of update client
	 */
	@Test
	public void updateClientTest() {

		clientService.saveClient(testClient);
		Client findServiceClient = findClientService.findByEmail(testClient);

		testClient.setClientName("Manager");
		testClient.setEmail("Manager@gmail.com");
		testClient.setPassword("Manager");

		assertEquals(clientService.findClient(findServiceClient.getId()).getId(), findServiceClient.getId());

		clientService.updateClient(testClient, findServiceClient.getId());

		assertEquals(clientService.findClient(findServiceClient.getId()).getEmail(), testClient.getEmail());
	}

	/**
	 * Test of delete client
	 */
	@Test
	public void deleteClientTest() {

		clientService.saveClient(testClient);

		Client findServiceClient = findClientService.findByEmail(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getEmail(), testClient.getEmail());

		clientService.deleteClient(findServiceClient.getId());

		findServiceClient = findClientService.findByEmail(testClient);
		assertEquals(findServiceClient, null);
	}

	/**
	 * Test of update client's password
	 */
	@Test
	public void updatePasswordTest() {

		clientService.saveClient(testClient);

		Client findServiceClient = findClientService.findByEmail(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getEmail(), testClient.getEmail());

		testClient.setPassword("Manager");
		clientService.updatePassword(testClient, findServiceClient.getId());

		String md5HexPassword = DigestUtils.md5Hex(testClient.getPassword());
		assertEquals(clientService.findClient(findServiceClient.getId()).getPassword(), md5HexPassword);
	}

	/**
	 * Test social save of client
	 */
	@Test
	public void socialSaveTest() {

		clientService.saveSocialClient(testClient);

		testClient.setGoogleId("googleId");
		testClient.setFlag("google");
		clientService.saveSocialClient(testClient);
		Client findServiceClient = findClientService.findClient(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getGoogleId(), testClient.getGoogleId());

		testClient.setClientName(null);
		testClient.setEmail(null);
		testClient.setPassword(null);
		testClient.setFacebookId("facebookId");
		testClient.setFlag("facebook");
		clientService.saveSocialClient(testClient);
		findServiceClient = findClientService.findClient(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getFacebookId(), testClient.getFacebookId());

	}

	/**
	 * Test of add social info of client
	 */
	@Test
	public void addSocInfoTest() {

		clientService.saveClient(testClient);

		Client findServiceClient = findClientService.findByEmail(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getEmail(), testClient.getEmail());

		testClient.setGoogleId("googleId");
		testClient.setFlag("google");

		testClient.setId(findServiceClient.getId());
		clientService.addSocialInfo(testClient);
		assertEquals(clientService.findClient(findServiceClient.getId()).getGoogleId(), testClient.getGoogleId());
	}
}
