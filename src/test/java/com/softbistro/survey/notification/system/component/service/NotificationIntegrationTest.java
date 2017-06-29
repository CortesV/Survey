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
import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.component.service.NotificationDao;
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
	private final static String RECEIVER_EMAIL = "sashaalohin@ukr.net";
	private final static String SENDER_EMAIL = "softbistrosurvey@gmail.com";
	private final static Integer CLIENT_ID = 1;

	@Autowired
	private NotificationDao messageDao;

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private SurveyService surveyService;

	private Notification notification;

	@Before
	public void setUp() {
		notification = new Notification();
		notification.setBody("Body of notification");
		notification.setHeader("Header of notification");
		notification.setReceiverEmail(RECEIVER_EMAIL);
		notification.setSenderEmail(SENDER_EMAIL);

		messageDao.insertIntoNotification(notification);
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
		assertThat(messageDao.getAllEmailsToSending().get(0).getSenderEmail()).isEqualTo(SENDER_EMAIL);
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
		assertThat(clientDao.getNewClients().stream().filter(client -> client.getEmail().equals(SENDER_EMAIL))
				.findFirst().isPresent());
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

		Client client = new Client();
		client.setEmail(SENDER_EMAIL);
		client.setPassword("testemail");
		client.setFlag("google");

		clientDao.updatePassword(client, CLIENT_ID);

		assertNotEquals(clientDao.getClientUpdatePassword().size(), 0);
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
		assertNotEquals(messageDao.getAllEmailsToSending().size(), 0);
	}
}
