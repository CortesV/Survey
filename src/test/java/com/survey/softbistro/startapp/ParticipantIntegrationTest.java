package com.survey.softbistro.startapp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.participant.components.dao.AttributeValuesDao;
import com.softbistro.survey.participant.components.dao.AttributesDao;
import com.softbistro.survey.participant.components.dao.GroupDao;
import com.softbistro.survey.participant.components.dao.ParticipantDao;
import com.softbistro.survey.participant.components.dao.ParticipantInGroupDao;
import com.softbistro.survey.participant.components.entity.AttributeValues;
import com.softbistro.survey.participant.components.entity.Attributes;
import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
import com.softbistro.survey.startapp.SurveySoftBistroApplication;

/**
 * Integration test for participant dao
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
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private ParticipantInGroupDao participantInGroupDao;
	
	@Autowired
	private AttributesDao attributesDao;
	
	@Autowired
	private AttributeValuesDao attributeValuesDao;

	private AttributeValues attributeValuesTest;
	private Attributes attributesTest;
	private Group groupTest;
	private Participant participantTest;	
	private ParticipantInGroup participantInGroupTest;


	@Before
	public void setUp() {

		participantTest = new Participant();
		participantTest.setClientId(Integer.MAX_VALUE);
		participantTest.setFirstName("firstName");
		participantTest.setLastName("lastName");
		participantTest.seteMail("eMail");
		
		groupTest = new Group();
		groupTest.setClientId(Integer.MAX_VALUE);
		groupTest.setGroupName("groupName");
		
		participantInGroupTest = new ParticipantInGroup();
		attributesTest = new Attributes();
		attributeValuesTest = new AttributeValues();
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
		
		Integer id = participantDao.setParticipant(participantTest);
		participantTest.seteMail("Update email");
		participantDao.updateParticipant(participantTest, id);
		assertEquals(participantDao.getParticipantById(id).getClientId(), participantTest.getClientId());
	}
	
	/**
	 * Test delete participant
	 */
	@Test
	public void deleteParticipantTest() {
		
		Integer id = participantDao.setParticipant(participantTest);
		participantDao.deleteParticipantById(id);
		assertEquals(participantDao.getParticipantById(id), null);
	}
	
	/**
	 * Test getting participant from db by attribute value
	 */
	@Test
	public void getParticipantByAttributeValueTest() {
		
		Integer idParticipant = participantDao.setParticipant(participantTest);
		Integer idGroup = groupDao.setGroup(groupTest);
		
		attributesTest.setGroupId(idGroup);
		attributesTest.setAttribute("attribute");
		Integer idAttribute = attributesDao.setAttribute(attributesTest);
		
		attributeValuesTest.setParticipantId(idParticipant);
		attributeValuesTest.setAttributeId(idAttribute);
		attributeValuesTest.setValue("value");
		attributeValuesDao.setAttributeValues(attributeValuesTest);
		
		participantInGroupTest.setGroupId(idGroup);
		participantInGroupTest.setParticipantId(idParticipant);
		
		List<ParticipantInGroup> batch = new ArrayList<>();
		batch.add(participantInGroupTest);
		participantInGroupDao.addParticipantInGroup(batch);
		
		assertEquals(participantDao.getParticipantByAttributeValue(idAttribute, attributeValuesTest.getValue()).size(), 1);
	}
	
	/**
	 * Test getting participant from database by client id
	 */
	@Test
	public void selectClientAllParticipantsTest() {
		
		Integer idParticipant = participantDao.setParticipant(participantTest);
		Integer idGroup = groupDao.setGroup(groupTest);
		
		attributesTest.setGroupId(idGroup);
		attributesTest.setAttribute("attribute");
		Integer idAttribute = attributesDao.setAttribute(attributesTest);
		
		attributeValuesTest.setParticipantId(idParticipant);
		attributeValuesTest.setAttributeId(idAttribute);
		attributeValuesTest.setValue("value");
		attributeValuesDao.setAttributeValues(attributeValuesTest);
		
		participantInGroupTest.setGroupId(idGroup);
		participantInGroupTest.setParticipantId(idParticipant);
		
		List<ParticipantInGroup> batch = new ArrayList<>();
		batch.add(participantInGroupTest);
		participantInGroupDao.addParticipantInGroup(batch);
		
		assertEquals(participantDao.selectClientAllParticipants(groupTest.getClientId()).size(), 1);
	}
	

}
