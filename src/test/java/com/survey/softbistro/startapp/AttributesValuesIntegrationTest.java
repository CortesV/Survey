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
 * Integration test for attributes values
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class AttributesValuesIntegrationTest {
	
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

		attributeValuesTest = new AttributeValues();
		attributeValuesTest.setAttributeId(Integer.MAX_VALUE);
		attributeValuesTest.setParticipantId(Integer.MAX_VALUE);
		attributeValuesTest.setValue("value");
		
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
	}
	
	/**
	 * Test save attributes values
	 */
	@Test
	public void saveAttributeTest() {
		
		Integer id = attributeValuesDao.setAttributeValues(attributeValuesTest);
		assertEquals(attributeValuesDao.getAttributeValuesById(id).getValue(), attributeValuesTest.getValue());
	}
	
	/**
	 * Test update attributes values
	 */
	@Test
	public void updateAttributeTest() {
		
		Integer id = attributeValuesDao.setAttributeValues(attributeValuesTest);
		attributeValuesTest.setValue("Update attribute value");
		attributeValuesDao.updateAttributeValuesById(attributeValuesTest, id);
		assertEquals(attributeValuesDao.getAttributeValuesById(id).getValue(), attributeValuesTest.getValue());
	}
	
	/**
	 * Test delete attributes values
	 */
	@Test
	public void deleteAttributeTest() {
		
		Integer id = attributeValuesDao.setAttributeValues(attributeValuesTest);
		attributeValuesDao.deleteAttributeValuesById(id);
		assertEquals(attributeValuesDao.getAttributeValuesById(id), null);
	}
	
	/**
	 * Test getting all attribute values of participant in group
	 */
	@Test
	public void getParticipantAttributesInGroupTest() {
		
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

		assertEquals(attributeValuesDao.getParticipantAttributesInGroup(idGroup, idParticipant).size(), 1);
	}
}
