package com.softbistro.survey.question.components.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test of question dao
 * 
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class QuestionIntegrationTest {

	@Autowired
	private QuestionDao questionDao;

	private final Integer QUESTION_ID = 1;

	/**
	 * Test of save question to database
	 */
	@Test
	public void saveQuestionTest() {

		assertEquals(questionDao.findQuestionById(QUESTION_ID).getQuestion(), "IntegrationTestQuestion");
	}

	/**
	 * Test of find question in database by id
	 */
	@Test
	public void findQuestionTest() {

		assertEquals(questionDao.findQuestionById(QUESTION_ID).getQuestion(), "IntegrationTestQuestion");
	}

	/**
	 * Test of update question
	 */
	@Test
	public void updateQuestionTest() {
		Question testQuestion = new Question();

		testQuestion.setSurveyId(1);
		testQuestion.setQuestion("Question");
		testQuestion.setDescriptionShort("descriptionShort");
		testQuestion.setDescriptionLong("descriptionLong");
		testQuestion.setQuestionSectionId(1);
		testQuestion.setAnswerType("RATE1-10");
		testQuestion.setQuestionChoices("questionChoices");
		testQuestion.setRequiredComment(true);
		testQuestion.setRequired(true);

		questionDao.updateQuestion(testQuestion, QUESTION_ID);
		assertEquals(questionDao.findQuestionById(QUESTION_ID).getQuestion(), testQuestion.getQuestion());
	}

	/**
	 * Test of delete question
	 */
	@Test
	public void deleteQuestionTest() {
		questionDao.deleteQuestion(QUESTION_ID);
		assertEquals(questionDao.findQuestionById(QUESTION_ID), null);
	}
}
