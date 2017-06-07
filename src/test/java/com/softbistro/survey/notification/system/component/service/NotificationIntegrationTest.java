package com.softbistro.survey.notification.system.component.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

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
import com.softbistro.survey.creating.survey.service.SurveyService;
import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.component.service.NotificationDao;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for MessageDao
 * 
 * @author alex_alokhin
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class NotificationIntegrationTest {

	private final static Integer SURVEY_ID = 1;
	private final static String PARTICIPANT_EMAIL = "softbistrosurvey@gmail.com";

	@Autowired
	private NotificationDao messageDao;

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private SurveyService surveyService;

	Notification notification;

	@Before
	public void setUp() {
		notification = new Notification();
		notification.setBody("Body of notification");
		notification.setHeader("Header of notification");
		notification.setReceiverEmail("sashaalohin@ukr.net");
		notification.setSenderEmail("softbistrosurvey@gmail.com");

	}

	/**
	 * Insert notification into table
	 * 
	 * @param notification
	 *            - new record
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void insertIntoNotificationTest() {
		messageDao.insertIntoNotification(notification);
		assertThat(messageDao.getEmailsToSendingForThread().get(0).getBody()).isEqualTo(notification.getBody());
	}

	/**
	 * Get mails of clients that have registration process
	 * 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void getNewClientsTest() {
		Client testClientNew = new Client();
		testClientNew.setId(111);
		testClientNew.setClientName("vanyas");
		testClientNew.setEmail("vanss@gmail.com");
		testClientNew.setPassword("1234");
		clientDao.saveClient(testClientNew);
		assertThat(
				clientDao.getNewClients().stream().filter(client -> client.getEmail().equals(testClientNew.getEmail())
						&& client.getId().equals(testClientNew.getId())).findFirst().isPresent());
	}

	/**
	 * Get mails of clients that change password
	 * 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void getEmailOfNewPasswordTest() {

		Client testClient = new Client();
		testClient.setClientName("SurveyManager");
		testClient.setEmail("SurveyManager@gmail.com");
		testClient.setPassword("SurveyManager");
		testClient.setGoogleId("googleId");
		testClient.setFlag("google");

		Integer clientId = clientDao.saveClient(testClient);

		Client findServiceClient = clientDao.findClient(clientId);
		testClient.setPassword("Manager");
		clientDao.updatePassword(testClient, findServiceClient.getId());

		assertThat(clientDao.getClientUpdatePassword().get(0).getEmail()).isEqualTo(testClient.getEmail());
	}

	/**
	 * Get mails of clients that started the survey
	 * 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void getClientsForSendingSurveyTest() {

		surveyService.start(SURVEY_ID);
		assertNotEquals(clientDao.getClientsForSendingSurvey().size(), 0);
	}

	/**
	 * Get an e-mails that need to send
	 * 
	 * @return List<Notification>
	 */
	@Test
	public void getEmailsForSendingTest() {
		messageDao.insertIntoNotification(notification);
		assertThat(messageDao.getEmailsToSendingForThread().get(0).getHeader()).isEqualTo(notification.getHeader());
	}
}
