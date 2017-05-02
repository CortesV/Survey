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

import com.softbistro.survey.participant.component.entity.Attributes;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for attributes dao
 * 
 * @author cortes
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class AttributesIntegrationTest {

	@Autowired
	private AttributesDao attributesDao;

	private Attributes attributesTest;

	private final Integer ATTRIBUTES_ID = 1;

	@Before
	public void setUp() {

		attributesTest = new Attributes();
		attributesTest.setGroupId(Integer.MAX_VALUE);
		attributesTest.setAttribute("attribute");
	}

	/**
	 * Test save attributes
	 */
	@Test
	public void saveAttributeTest() {

		Integer id = attributesDao.setAttribute(attributesTest);
		assertEquals(attributesDao.getAttributeById(id).getAttribute(), attributesTest.getAttribute());
	}

	/**
	 * Test update attributes
	 */
	@Test
	public void updateAttributeTest() {

		attributesDao.setAttribute(attributesTest);
		attributesTest.setAttribute("Update attribute");

		attributesDao.updateAttributes(attributesTest, ATTRIBUTES_ID);

		assertEquals(attributesDao.getAttributeById(ATTRIBUTES_ID).getAttribute(), attributesTest.getAttribute());
	}

	/**
	 * Test delete attributes
	 */
	@Test
	public void deleteAttributeTest() {

		attributesDao.deleteAttributes(ATTRIBUTES_ID);
		assertEquals(attributesDao.getAttributeById(ATTRIBUTES_ID), null);
	}

	/**
	 * Test get all attributes by groups
	 */
	@Test
	public void getByGroupAttributeTest() {

		assertNotEquals(
				attributesDao.getAttributesByGroup(attributesDao.getAttributeById(ATTRIBUTES_ID).getGroupId()).size(),
				0);
	}
}
