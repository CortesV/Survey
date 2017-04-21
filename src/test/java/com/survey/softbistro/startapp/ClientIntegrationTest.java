package com.survey.softbistro.startapp;

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
import com.softbistro.survey.client.manage.components.service.ClientDao;
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
		testClient.setGoogleId("googleId");
		testClient.setFlag("google");

		clientService.saveClient(testClient);
	}

	/**
	 * Method that test save client to database
	 */
	@Test
	public void saveClientTest() {

		assertThat(findClientService.findByEmail(testClient).getEmail()).as("Email = ")
				.isEqualTo(testClient.getEmail());
	}

	/**
	 * Method that test get client by id
	 */
	@Test
	public void getClientTest() {

		assertThat(clientService.findClient(findClientService.findByEmail(testClient).getId()).getEmail())
				.as("Email = ").isEqualTo(testClient.getEmail());
	}

	/**
	 * Test of update client
	 */
	@Test
	public void updateClientTest() {

		Client findServiceClient = findClientService.findByEmail(testClient);
		testClient.setClientName("Manager");
		testClient.setEmail("Manager@gmail.com");
		testClient.setPassword("Manager");		
		clientService.updateClient(testClient, findServiceClient.getId());
		assertThat(clientService.findClient(findServiceClient.getId()).getEmail()).as("Email = ").isEqualTo(testClient.getEmail());
	}

	/**
	 * Test of delete client
	 */
	@Test
	public void deleteClientTest() {

		clientService.deleteClient(findClientService.findByEmail(testClient).getId());
		assertThat(findClientService.findByEmail(testClient)).as("Object = ").isEqualTo(null);
	}

	/**
	 * Test of update client's password
	 */
	@Test
	public void updatePasswordTest() {

		Client findServiceClient = findClientService.findByEmail(testClient);

		testClient.setPassword("Manager");
		clientService.updatePassword(testClient, findServiceClient.getId());

		String md5HexPassword = DigestUtils.md5Hex(testClient.getPassword());
		assertThat(clientService.findClient(findServiceClient.getId()).getPassword()).as("Password = ").isEqualTo(md5HexPassword);
	}

	/**
	 * Test social save of client
	 */
	@Test
	public void socialSaveTest() {

		testClient.setClientName(null);
		testClient.setEmail(null);
		testClient.setPassword(null);
		testClient.setFacebookId("facebookId");
		testClient.setFlag("facebook");
		clientService.saveSocialClient(testClient);

		assertThat(findClientService.findClient(testClient).getFacebookId()).as("FacebookId = ").isEqualTo(testClient.getFacebookId());

	}

	/**
	 * Test of add social info of client
	 */
	@Test
	public void addSocInfoTest() {

		Client findServiceClient = findClientService.findByEmail(testClient);

		testClient.setId(findServiceClient.getId());
		testClient.setFacebookId("facebookId");
		testClient.setGoogleId(null);
		
		clientService.addSocialInfo(testClient);
		findServiceClient = findClientService.findByEmail(testClient);
		assertThat(clientService.findClient(findServiceClient.getId()).getFacebookId()).as("FacebookId = ").isEqualTo(testClient.getFacebookId());
	}
}
