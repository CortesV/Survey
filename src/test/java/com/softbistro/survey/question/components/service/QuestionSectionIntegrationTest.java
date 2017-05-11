package com.softbistro.survey.question.components.service;

import static org.junit.Assert.assertEquals;

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

import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for question section dao
 * 
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
@Rollback
public class QuestionSectionIntegrationTest {

	@Autowired
	private QuestionSectionDao questionSectionDao;

	private QuestionSection questionSectionTest;

	private final Integer SURVEY_ID = 1;

	private final Integer QUESTION_SECTION_ID = 1;

	@Before
	public void prepareData() {
		questionSectionTest = new QuestionSection();
		questionSectionTest.setClientId(1);
		questionSectionTest.setSectionName("sectionName");
		questionSectionTest.setDescriptionShort("descriptionShort");
		questionSectionTest.setDescriptionLong("descrioptionLong");
	}

	/**
	 * Test of save question section to database
	 */
	@Test
	public void saveQuestionSectionTest() {

		Integer savedQuestionSectionId = questionSectionDao.setQuestionSection(questionSectionTest);
		assertEquals(questionSectionDao.getQuestionSectionById(savedQuestionSectionId).getSectionName(),
				questionSectionTest.getSectionName());
	}

	/**
	 * Test of update question section
	 */
	@Test
	public void updateQuestionSectionTest() {
		questionSectionDao.updateQuestionSection(questionSectionTest, QUESTION_SECTION_ID);
		assertEquals(questionSectionDao.getQuestionSectionById(QUESTION_SECTION_ID).getSectionName(),
				questionSectionTest.getSectionName());
	}

	/**
	 * Test of delete question section
	 */
	@Test
	public void deleteQuestionSectionTest() {
		questionSectionDao.deleteQuestionSection(QUESTION_SECTION_ID);
		assertEquals(questionSectionDao.getQuestionSectionById(QUESTION_SECTION_ID), null);
	}

	/**
	 * Test of get question sections by client id
	 */
	@Test
	public void getByClientIdQuestionSectionTest() {
		assertEquals(questionSectionDao.getQuestionSectionByClientId(questionSectionTest.getClientId()).size(), 1);
	}

	/**
	 * Test of add record to table "connect_question_section_survey" of database
	 */
	@Test
	public void addQuestionSectionToSurveyTest() {
		Integer sizeBeforeAdding = questionSectionDao.getQuestionSectionBySurveyId(SURVEY_ID).size();
		questionSectionDao.addQuestionSectionToSurvey(QUESTION_SECTION_ID, SURVEY_ID);
		assertEquals(questionSectionDao.getQuestionSectionBySurveyId(SURVEY_ID).size(), sizeBeforeAdding + 1);
	}

	/**
	 * Test delete record from table "connect_question_section_survey"
	 */
	@Test
	public void deleteQuestionSectionFromSurveyTest() {

		questionSectionDao.deleteQuestionSectionFromSurvey(QUESTION_SECTION_ID, SURVEY_ID);
		assertEquals(questionSectionDao.getQuestionSectionBySurveyId(SURVEY_ID).size(), 0);
	}
}
