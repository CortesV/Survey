package com.survey.softbistro.startapp;

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

import com.softbistro.survey.participant.components.dao.GroupDao;
import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.startapp.SurveySoftBistroApplication;

/**
 * Integration test for group dao
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

	@Before
	public void setUp() {

		groupTest = new Group();
		groupTest.setClientId(Integer.MAX_VALUE);
		groupTest.setGroupName("groupName");;
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
		
		Integer id = groupDao.setGroup(groupTest);
		groupTest.setGroupName("Update group name");
		groupDao.updateGroupById(groupTest, id);
		assertEquals(groupDao.getGroupByid(id).getGroupName(), groupTest.getGroupName());
	}
	
	/**
	 * Test delete group
	 */
	@Test
	public void deleteGroupTest() {
		
		Integer id = groupDao.setGroup(groupTest);
		groupDao.deleteGroupById(id);
		assertEquals(groupDao.getGroupByid(id), null);
	}

	/**
	 * Test get all clients in group
	 */
	@Test
	public void getGroupsByClientTest() {
		
		Integer id = groupDao.setGroup(groupTest);
		assertEquals(groupDao.getGroupsByClient(groupDao.getGroupByid(id).getClientId()).size(), 1);
	}

}
