package com.survey.softbistro.startapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.client.auth.components.entities.AuthorizedClient;
import com.softbistro.survey.client.auth.service.AuthorizedClientService;
import com.softbistro.survey.startapp.SurveySoftBistroApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
@Rollback(true)
public class AuthorizedClientIntegrationTest {

	@Autowired
	AuthorizedClientService authorizedClientService;
	
	AuthorizedClient authorizedClientTest;
	
	@Before
	public void setUp() {
		
		authorizedClientTest = new AuthorizedClient();
		authorizedClientTest.setUniqueKey("c428d4bc-fde5-4eef-b4cb-b8f48a72ffb2");
		authorizedClientTest.setClientId("1");
		authorizedClientTest.setTimeValidKey(10);
	}
	
	@Test
	public void saveAuthorizedClientTest(){
		
		authorizedClientService.saveClient(authorizedClientTest);
		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey(), authorizedClientTest.getUniqueKey());
	}
	
	@Test
	public void updateAuthorizedClientTest(){
		
		authorizedClientService.saveClient(authorizedClientTest);
		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey(), authorizedClientTest.getUniqueKey());
		
		authorizedClientTest.setClientId("2");
		
		authorizedClientService.updateClient(authorizedClientTest);
		
		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getClientId(), authorizedClientTest.getClientId());
	}
	
	@Test
	public void deleteAuthorizedClientTest(){
		
		authorizedClientService.saveClient(authorizedClientTest);
		assertEquals(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey(), authorizedClientTest.getUniqueKey());
		
		authorizedClientService.deleteClients(authorizedClientService.findClient(authorizedClientTest.getUniqueKey()).getUniqueKey());
	}
	
	@Test
	public void findAllAuthorizedClientTest(){
		
		authorizedClientService.saveClient(authorizedClientTest);
		assertEquals(authorizedClientService.findAllClients().size(), 1);
	}
}
