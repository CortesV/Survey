package com.survey.softbistro.startapp;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.softbistro.survey.creating.survey.service.SurveyService;
import com.softbistro.survey.daemons.notification.system.component.dao.MessageDao;
import com.softbistro.survey.daemons.notification.system.component.entity.Notification;
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

	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private FindClientService findClientService;
	
	@Autowired
	private SurveyService surveyService;
	
	Notification notification;
	
	@Before
	public void setUp(){
		notification = new Notification();
		notification.setBody("Body of notification");
		notification.setHeader("Header of notification");
		notification.setReceiverEmail("sashaalohin@ukr.net");
		notification.setSenderEmail("softbistrosurvey@gmail.com");
		
	}
	
	/**
	 * Insert notification into table
	 * @param notification - new record
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void insertIntoNotificationTest(){
		messageDao.insertIntoNotification(notification);
		assertThat(messageDao.getEmailsForSending().get(0).getBody()).isEqualTo(notification.getBody());
	}
	
	/**
	 * Get mails of clients that have registration process
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void getEmailOfNewClientsTest(){
		Client testClientNew = new Client();
		testClientNew.setClientName("vanyasv");
		testClientNew.setEmail("vanss@gmail.come");
		testClientNew.setPassword("1234");
		
		clientDao.saveClient(testClientNew);
		assertThat(messageDao.getEmailOfNewClients().get(0)).isEqualTo(testClientNew.getEmail());
	}

	/**
	 * Get mails of clients that change password 
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void getEmailOfNewPasswordTest(){
		Client testClientPass = new Client();
		testClientPass.setClientName("sanyaSANYA");
		testClientPass.setEmail("sashaalohin@ukr.net");
		
		Client findServiceClient = findClientService.findByEmail(testClientPass);
		testClientPass.setPassword("Manager");
		clientDao.updatePassword(testClientPass, findServiceClient.getId());
		assertThat(messageDao.getEmailOfNewPassword().get(0)).isEqualTo(testClientPass.getEmail());
	}
	
	/**
	 * Get mails of clients that started the survey
	 * @return - list of mails
	 * 
	 * @author alex_alokhin
	 */
	@Test
	public void getEmailsForSendingSurveyTest(){
		int surveyId = 10;
		String participantEmail = "eugene@kushta.eu";
		
		surveyService.start(surveyId);
		assertThat(messageDao.getEmailsForSendingSurvey().get(0)).isEqualTo(participantEmail);
	}
	
	/**
	 * Get an e-mails that need to send
	 * 
	 * @return List<Notification>
	 */
	@Test
	public void getEmailsForSendingTest(){
		messageDao.insertIntoNotification(notification);
		assertThat(messageDao.getEmailsForSending().get(0).getHeader()).isEqualTo(notification.getHeader());
	}
}
