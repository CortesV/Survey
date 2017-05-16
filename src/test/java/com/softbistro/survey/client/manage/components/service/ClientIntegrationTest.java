package com.softbistro.survey.client.manage.components.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.softbistro.survey.client.manage.service.FindClientService;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

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
	private ClientDao clientDao;

	@Autowired
	private FindClientService findClientService;

	private Client testClient;

	private Integer clientId;

	@Before
	public void setUp() {

		testClient = new Client();
		testClient.setClientName("SurveyManager");
		testClient.setEmail("SurveyManager@gmail.com");
		testClient.setPassword("SurveyManager");
		testClient.setGoogleId("googleId");
		testClient.setFlag("google");

		clientId = clientDao.saveClient(testClient);
	}

	/**
	 * Method that test save client to database
	 */
	@Test
	public void saveClientTest() {

		assertThat(clientDao.findClient(clientId).getEmail()).as("Email = ").isEqualTo(testClient.getEmail());
	}

	/**
	 * Method that test get client by id
	 */
	@Test
	public void getClientTest() {

		assertThat(clientDao.findClient(clientId).getEmail()).as("Email = ").isEqualTo(testClient.getEmail());
	}

	/**
	 * Test of update client
	 */
	@Test
	public void updateClientTest() {

		Client findServiceClient = clientDao.findClient(clientId);
		testClient.setClientName("Manager");
		testClient.setEmail("Manager@gmail.com");
		testClient.setPassword("Manager");
		clientDao.updateClient(testClient, findServiceClient.getId());
		assertThat(clientDao.findClient(findServiceClient.getId()).getEmail()).as("Email = ")
				.isEqualTo(testClient.getEmail());
	}

	/**
	 * Test of delete client
	 */
	@Test
	public void deleteClientTest() {

		clientDao.deleteClient(clientId);
		assertThat(findClientService.findByEmail(testClient)).as("Object = ").isEqualTo(null);
	}

	/**
	 * Test of update client's password
	 */
	@Test
	public void updatePasswordTest() {

		Client findServiceClient = findClientService.findByEmail(testClient);

		testClient.setPassword("Manager");
		clientDao.updatePassword(testClient, clientId);

		String md5HexPassword = DigestUtils.md5Hex(testClient.getPassword());
		assertThat(clientDao.findClient(clientId).getPassword()).as("Password = ").isEqualTo(md5HexPassword);
	}

	/**
	 * Test social save of client
	 */
	@Test
	public void socialSaveTest() {

		testClient = clientDao.findClient(1);
		testClient.setFacebookId("facebookId");
		testClient.setFlag("facebook");
		clientDao.saveSocialClient(testClient);

		if (findClientService.findClient(testClient) == null) {

			assertThat(clientDao.findClient(1).getFacebookId()).as("FacebookId = ")
					.isEqualTo(testClient.getFacebookId());
		} else {

			assertThat(findClientService.findClient(testClient).getFacebookId()).as("FacebookId = ")
					.isEqualTo(testClient.getFacebookId());
		}

	}

	/**
	 * Test of add social info of client
	 */
	@Test
	public void addSocInfoTest() {

		Client findServiceClient = clientDao.findClient(clientId);

		testClient.setId(findServiceClient.getId());
		testClient.setFacebookId("facebookId");
		testClient.setGoogleId(null);

		clientDao.addSocialInfo(testClient);
		findServiceClient = findClientService.findByEmail(testClient);
		assertThat(clientDao.findClient(clientId).getFacebookId()).as("FacebookId = ")
				.isEqualTo(testClient.getFacebookId());
	}
}
