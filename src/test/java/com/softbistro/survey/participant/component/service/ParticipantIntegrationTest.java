package com.softbistro.survey.participant.component.service;

import static org.junit.Assert.assertEquals;
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

import com.softbistro.survey.participant.component.entity.AttributeValues;
import com.softbistro.survey.participant.component.entity.Attributes;
import com.softbistro.survey.participant.component.entity.Group;
import com.softbistro.survey.participant.component.entity.Participant;
import com.softbistro.survey.participant.component.entity.ParticipantInGroup;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for participant dao
 * 
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class ParticipantIntegrationTest {

	@Autowired
	private ParticipantDao participantDao;

	private final Integer PARTICIPANT_ID = 1;
	private AttributeValues attributeValuesTest;
	private Attributes attributesTest;
	private Group groupTest;
	private Participant participantTest;
	private ParticipantInGroup participantInGroupTest;

	@Before
	public void setUp() {

		participantTest = new Participant();
		participantTest.setClientId(1);
		participantTest.setFirstName("firstName");
		participantTest.setLastName("lastName");
		participantTest.seteMail("eMail");

	}

	/**
	 * Test save participant
	 */
	@Test
	public void saveParticipantTest() {

		Integer id = participantDao.setParticipant(participantTest);
		assertEquals(participantDao.getParticipantById(id).getClientId(), participantTest.getClientId());
	}

	/**
	 * Test update participant
	 */
	@Test
	public void updateParticipantTest() {

		participantTest.seteMail("Update email");
		participantDao.updateParticipant(participantTest, PARTICIPANT_ID);
		assertEquals(participantDao.getParticipantById(PARTICIPANT_ID).geteMail(), participantTest.geteMail());
	}

	/**
	 * Test delete participant
	 */
	@Test
	public void deleteParticipantTest() {

		participantDao.deleteParticipantById(PARTICIPANT_ID);
		assertEquals(participantDao.getParticipantById(PARTICIPANT_ID), null);
	}

	/**
	 * Test getting participant from db by attribute value
	 */
	@Test
	public void getParticipantByAttributeValueTest() {

		assertNotEquals(participantDao.getParticipantByAttributeValue(1, "IntegrationTestsAttributeValue").size(), 0);
	}

	/**
	 * Test getting participant from database by client id
	 */
	@Test
	public void selectClientAllParticipantsTest() {

		assertNotEquals(participantDao.selectClientAllParticipants(1).size(), 0);
	}

}
