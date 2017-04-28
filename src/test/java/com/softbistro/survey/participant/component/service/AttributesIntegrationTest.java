package com.softbistro.survey.participant.component.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.participant.dao.AttributesDao;
import com.softbistro.survey.participant.entity.Attributes;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for attributes dao
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
		
		Integer id = attributesDao.setAttribute(attributesTest);		
		attributesTest.setAttribute("Update attribute");
		attributesDao.updateAttributes(attributesTest, id);
		assertEquals(attributesDao.getAttributeById(id).getAttribute(), attributesTest.getAttribute());
	}
	
	/**
	 * Test delete attributes
	 */
	@Test
	public void deleteAttributeTest() {
		
		Integer id = attributesDao.setAttribute(attributesTest);
		attributesDao.deleteAttributes(id);
		assertEquals(attributesDao.getAttributeById(id), null);
	}
	
	/**
	 * Test get all attributes by groups
	 */
	@Test
	public void getByGroupAttributeTest() {
		
		Integer id = attributesDao.setAttribute(attributesTest);
		assertEquals(attributesDao.getAttributesByGroup(attributesDao.getAttributeById(id).getGroupId()).size(), 1);
	}
}
