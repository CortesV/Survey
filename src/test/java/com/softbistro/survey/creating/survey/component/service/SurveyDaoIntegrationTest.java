package com.softbistro.survey.creating.survey.component.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Working with survey
 * 
 * @author zviproject
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
@Rollback
public class SurveyDaoIntegrationTest {

	@Autowired
	private SurveyDao surveyDao;

	private Survey survey;

	private final Integer CLIENT_ID = 1;

	private final Integer SURVEY_ID = 1;

	@Before
	public void prepareData() {
		survey = new Survey();
		survey.setClientId(Integer.MAX_VALUE);
		survey.setFinishTime(new Date(System.currentTimeMillis()));
		survey.setStartTime(new Date(System.currentTimeMillis()));
		survey.setSurveyName("TestName");
		survey.setSurveyTheme("TestTheme");
	}

	/**
	 * Writing new survey into database.
	 * 
	 */
	@Test
	public void createTest() {
		assertNotEquals(surveyDao.create(survey).intValue(), 0);

	}

	/**
	 * Update name of survey in database
	 * 
	 */
	@Test
	public void update() {
		survey.setSurveyName("NewTestName");
		surveyDao.update(survey);
	}

	/**
	 * Get all surveys from one client
	 * 
	 */
	@Test
	public void getAllSurveysByClient() {
		assertNotEquals(surveyDao.getAllSurveysByClient(Integer.MAX_VALUE).size(), 0);
	}

	/**
	 * Get all groups that has client
	 * 
	 */
	@Test
	public void getGroupsClient() {
		assertNotEquals(surveyDao.getGroupsClient(CLIENT_ID), 0);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 */
	@Test
	public void addGroups() {
		Group group = new Group();
		group.setClientId(Integer.MAX_VALUE);
		group.setGroupName("TestGroupName");
		group.setSurveyId(SURVEY_ID);
		group.setId(1);

		List<Group> groups = new ArrayList<>();
		groups.add(group);

		surveyDao.addGroups(groups);

		assertNotEquals(surveyDao.getGroupsClient(Integer.MAX_VALUE), 0);
	}

	/**
	 * Get all groups that has survey
	 * 
	 */
	@Test
	public void getGroupsSurvey() {
		assertNotEquals(surveyDao.getGroupsSurvey(SURVEY_ID), 0);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 */
	@Test
	public void delete() {
		Integer idCreatedSurvey = surveyDao.create(survey);
		int sizeBeforeDeleting = surveyDao.getGroupsClient(CLIENT_ID).size();
		surveyDao.delete(idCreatedSurvey);
		assertEquals(surveyDao.getGroupsClient(CLIENT_ID).size(), sizeBeforeDeleting);
	}

	/**
	 * Start survey
	 * 
	 */
	@Test
	public void start() {
		surveyDao.start(SURVEY_ID);
	}

	/**
	 * Stop survey
	 * 
	 */
	@Test
	public void stop() {
		surveyDao.stop(SURVEY_ID);
	}

}
