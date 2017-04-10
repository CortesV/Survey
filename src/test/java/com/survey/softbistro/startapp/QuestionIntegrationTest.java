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

import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.question.components.service.QuestionDao;
import com.softbistro.survey.startapp.SurveySoftBistroApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class QuestionIntegrationTest {

	@Autowired
	private QuestionDao questionService;

	private Question testQuestion;

	@Before
	public void setUp() {

		testQuestion = new Question();
		testQuestion.setSurveyId(1);
		testQuestion.setQuestion("Question");
		testQuestion.setDescriptionShort("descriptionShort");
		testQuestion.setDescriptionLong("descriptionLong");
		testQuestion.setQuestionSectionId(1);
		testQuestion.setAnswerType("RATE1-10");
		testQuestion.setQuestionChoices("questionChoices");
		testQuestion.setRequiredComment(true);
		testQuestion.setRequired(true);
	}

	@Test
	public void saveQuestionTest() {

		Integer id = questionService.saveQuestion(testQuestion);
		assertEquals(questionService.findQuestionById(id).getQuestion(), testQuestion.getQuestion());
	}
	
	@Test
	public void findQuestionTest() {

		Integer id = questionService.saveQuestion(testQuestion);		
		assertEquals(questionService.findQuestionById(id).getId(), id);
	}
	
	@Test
	public void updateQuestionTest() {

		Integer id = questionService.saveQuestion(testQuestion);
		testQuestion.setQuestion("Question-Question");
		questionService.updateQuestion(testQuestion, id);
		assertEquals(questionService.findQuestionById(id).getQuestion(), testQuestion.getQuestion());
	}
	
	@Test
	public void deleteQuestionTest() {

		Integer id = questionService.saveQuestion(testQuestion);
		questionService.deleteQuestion(id);
		assertEquals(questionService.findQuestionById(id), null);
	}
}
