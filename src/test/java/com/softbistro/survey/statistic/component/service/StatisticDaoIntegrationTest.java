package com.softbistro.survey.statistic.component.service;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

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

import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.creating.survey.component.service.SurveyDao;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
import com.softbistro.survey.statistic.component.service.StatisticDao;

/**
 * Test exporting statistic
 * 
 * @author zviproject
 *
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@Rollback
@Transactional
public class StatisticDaoIntegrationTest {
	@Autowired
	private SurveyDao surveyDao;

	@Autowired
	private StatisticDao statisticDao;

	private String surveyName = "SurveyTestName";

	private Integer surveyId;

	private Integer clientId = 2;
	private String surveyTheme = "TestSurveyTheme";

	/**
	 * Save the survey for further work with him
	 */
	@Before
	public void insertData() {
		Date date = new Date(System.currentTimeMillis());
		Survey survey = new Survey();
		survey.setSurveyName(surveyName);
		survey.setClientId(clientId);
		survey.setSurveyTheme(surveyTheme);
		survey.setStartTime(date);
		survey.setFinishTime(date);

		surveyId = surveyDao.create(survey);
	}

	/**
	 * Display short statistic of survey
	 */
	@Test
	public void surveyTest() {
		SurveyStatisticShort surveyStatisticShort = statisticDao.survey(surveyId);

		assertEquals(surveyStatisticShort.getName(), surveyName);
	}

	/**
	 * Export statistic on google sheets
	 */
	@Test
	public void exportTest() {
		statisticDao.export(surveyId);
	}
}
