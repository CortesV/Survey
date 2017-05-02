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
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for attributes values
 * 
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
	private AttributeValuesDao attributeValuesDao;

	private final Integer ATTRIBUTE_VALUES_ID = 1;
	private AttributeValues attributeValuesTest;

	@Before
	public void setUp() {

		attributeValuesTest = new AttributeValues();
		attributeValuesTest.setAttributeId(Integer.MAX_VALUE);
		attributeValuesTest.setParticipantId(Integer.MAX_VALUE);
		attributeValuesTest.setValue("value");
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

		attributeValuesTest.setValue("Update attribute value");

		attributeValuesDao.updateAttributeValuesById(attributeValuesTest, ATTRIBUTE_VALUES_ID);

		assertEquals(attributeValuesDao.getAttributeValuesById(ATTRIBUTE_VALUES_ID).getValue(),
				attributeValuesTest.getValue());
	}

	/**
	 * Test delete attributes values
	 */
	@Test
	public void deleteAttributeTest() {

		attributeValuesDao.deleteAttributeValuesById(ATTRIBUTE_VALUES_ID);
		assertEquals(attributeValuesDao.getAttributeValuesById(ATTRIBUTE_VALUES_ID), null);
	}

	/**
	 * Test getting all attribute values of participant in group
	 */
	@Test
	public void getParticipantAttributesInGroupTest() {

		assertNotEquals(attributeValuesDao.getParticipantAttributesInGroup(1, 1).size(), 0);
	}
}
