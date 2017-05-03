package com.softbistro.survey.statistic.component.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.standalone.SurveySoftBistroApplication;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;

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
	private StatisticDao statisticDao;

	private final Integer SURVEY_ID = 1;

	/**
	 * Display short statistic of survey
	 */
	@Test
	public void surveyTest() {
		SurveyStatisticShort surveyStatisticShort = statisticDao.survey(SURVEY_ID);

		assertEquals(surveyStatisticShort.getName(), "IntegrationTestName");
	}

	/**
	 * Export statistic on google sheets
	 */
	@Test
	public void exportTest() {
		statisticDao.export(SURVEY_ID);
	}
}
