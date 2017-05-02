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

import com.softbistro.survey.participant.component.entity.ParticipantInGroup;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for participant in group
 * 
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class ParticipantInGroupIntegrationTest {

	@Autowired
	private ParticipantInGroupDao participantInGroupDao;

	private final Integer PARTICIPANT_ID = 1;

	private ParticipantInGroup participantInGroupTest;

	@Before
	public void setUp() {

		participantInGroupTest = new ParticipantInGroup();

		participantInGroupTest.setParticipantId(PARTICIPANT_ID);
		participantInGroupTest.setGroupId(1);
	}

	/**
	 * Test adding participant in group
	 */
	@Test
	public void saveParticipantInGroupTest() {

		participantInGroupDao.addParticipantInGroup(participantInGroupTest);
		assertNotEquals(participantInGroupDao.getParticipantsByGroup(participantInGroupTest.getGroupId()).size(), 0);
	}

	/**
	 * Test deleting participant from group
	 */
	@Test
	public void deleteParticipantInGroupTest() {
		Integer sizeBeforeDeleting = participantInGroupDao.getParticipantsByGroup(participantInGroupTest.getGroupId())
				.size();

		participantInGroupDao.deletingParticipantfromGroup(1, 1);
		assertEquals(participantInGroupDao.getParticipantsByGroup(participantInGroupTest.getGroupId()).size(),
				sizeBeforeDeleting - 1);
	}

	/**
	 * Test getting all participant groups
	 */
	@Test
	public void getParticipantGroupsTest() {

		assertNotEquals(participantInGroupDao.getParticipantGroups(PARTICIPANT_ID).size(), 0);
	}
}
