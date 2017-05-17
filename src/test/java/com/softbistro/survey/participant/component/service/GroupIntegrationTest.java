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

import com.softbistro.survey.participant.component.entity.Group;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for group dao
 * 
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class GroupIntegrationTest {

	@Autowired
	private GroupDao groupDao;

	private Group groupTest;

	private final Integer GROUP_ID = 1;

	@Before
	public void setUp() {

		groupTest = new Group();
		groupTest.setClientId(Integer.MAX_VALUE);
		groupTest.setGroupName("groupName");
		;
	}

	/**
	 * Test save group
	 */
	@Test
	public void saveGroupTest() {

		Integer id = groupDao.setGroup(groupTest);
		assertEquals(groupDao.getGroupByid(id).getGroupName(), groupTest.getGroupName());
	}

	/**
	 * Test update group
	 */
	@Test
	public void updateGroupTest() {

		groupTest.setGroupName("Update group name");
		groupDao.setGroup(groupTest);

		groupDao.updateGroupById(groupTest, GROUP_ID);
		assertEquals(groupDao.getGroupByid(GROUP_ID).getGroupName(), groupTest.getGroupName());
	}

	/**
	 * Test delete group
	 */
	@Test
	public void deleteGroupTest() {

		groupDao.deleteGroupById(GROUP_ID);
		assertEquals(groupDao.getGroupByid(GROUP_ID), null);
	}

	/**
	 * Test get all clients in group
	 */
	@Test
	public void getGroupsByClientTest() {

		assertNotEquals(groupDao.getGroupsByClient(groupDao.getGroupByid(GROUP_ID).getClientId()).size(), 0);
	}

}
