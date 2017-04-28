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

import com.softbistro.survey.participant.components.dao.GroupDao;
import com.softbistro.survey.participant.components.dao.ParticipantDao;
import com.softbistro.survey.participant.components.dao.ParticipantInGroupDao;
import com.softbistro.survey.participant.components.entity.Group;
import com.softbistro.survey.participant.components.entity.Participant;
import com.softbistro.survey.participant.components.entity.ParticipantInGroup;
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

	@Autowired
	private ParticipantDao participantDao;

	@Autowired
	private GroupDao groupDao;

	private Group groupTest;

	private ParticipantInGroup participantInGroupTest;

	private Participant participantTest;

	@Before
	public void setUp() {

		participantInGroupTest = new ParticipantInGroup();

		participantTest = new Participant();
		participantTest.setClientId(Integer.MAX_VALUE);
		participantTest.setFirstName("firstName");
		participantTest.setLastName("lastName");
		participantTest.seteMail("eMail");

		groupTest = new Group();
		groupTest.setClientId(Integer.MAX_VALUE);
		groupTest.setGroupName("groupName");
	}

	/**
	 * Test adding participant in group
	 */
	@Test
	public void saveParticipantInGroupTest() {

		Integer idParticipant = participantDao.setParticipant(participantTest);
		Integer idGroup = groupDao.setGroup(groupTest);

		participantInGroupTest.setGroupId(idGroup);
		// participantInGroupTest.setParticipantId(idParticipant);

		List<Integer> batch = new ArrayList<>();
		batch.add(idParticipant);
		participantInGroupTest.setParticipantsId(batch);

		participantInGroupDao.addParticipantInGroup(participantInGroupTest);
		assertEquals(participantInGroupDao.getParticipantsByGroup(participantInGroupTest.getGroupId()).size(), 1);
	}

	/**
	 * Test deleting participant from group
	 */
	@Test
	public void deleteParticipantInGroupTest() {

		Integer idParticipant = participantDao.setParticipant(participantTest);
		Integer idGroup = groupDao.setGroup(groupTest);

		participantInGroupTest.setGroupId(idGroup);

		List<Integer> batch = new ArrayList<>();
		batch.add(idParticipant);
		participantInGroupTest.setParticipantsId(batch);
		participantInGroupDao.addParticipantInGroup(participantInGroupTest);

		batch.add(Integer.MAX_VALUE);
		participantInGroupTest.setParticipantsId(batch);
		participantInGroupDao.addParticipantInGroup(participantInGroupTest);

		participantInGroupDao.deletingParticipantfromGroup(participantInGroupTest.getGroupId(),
				participantInGroupTest.getParticipantId());

		assertEquals(participantInGroupDao.getParticipantsByGroup(participantInGroupTest.getGroupId()).size(), 2);
	}

	/**
	 * Test getting all participant groups
	 */
	@Test
	public void getParticipantGroupsTest() {

		Integer idParticipant = participantDao.setParticipant(participantTest);
		Integer idGroup = groupDao.setGroup(groupTest);
		participantInGroupTest.setGroupId(idGroup);
		List<Integer> batch = new ArrayList<>();
		batch.add(idParticipant);
		participantInGroupTest.setParticipantsId(batch);
		participantInGroupDao.addParticipantInGroup(participantInGroupTest);
		assertEquals(participantInGroupDao.getParticipantGroups(idParticipant).size(), 1);
	}
}
