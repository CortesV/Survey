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

import com.softbistro.survey.imports.system.components.entities.GroupQuestions;
import com.softbistro.survey.imports.system.components.entities.Question;
import com.softbistro.survey.imports.system.components.entities.Survey;
import com.softbistro.survey.imports.system.components.services.SurveyDAO;
import com.softbistro.survey.startapp.SurveySoftBistroApplication;

/**
 * Integration test for import survey
 * @author cortes
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SurveySoftBistroApplication.class)
@WebAppConfiguration
@Transactional
public class ImportIntegrationTest {

	@Autowired
	private SurveyDAO surveyDAO;
	
	private List<Question> listQuestions;
	private List<GroupQuestions> listGroupQuestions;
	private Survey surveyTest;
	private GroupQuestions groupQuestionsTest;
	private Question questionTest, questionTest2, questionTest3;
	
	@Before
	public void setUp() {

		surveyTest = new Survey();
		surveyTest.setClienId(Long.MAX_VALUE);
		surveyTest.setTitle("Survey title");
		
		questionTest = new Question();
		questionTest.setText("text first question");
		questionTest.setRequired(true);
		questionTest.setRequiredComment(true);
		questionTest.setAnswerType("yes|no");
		questionTest.setQuestionChoices("value");
		listQuestions = new ArrayList<>();
		listQuestions.add(questionTest);
		
		questionTest2 = new Question();
		questionTest2.setText("text second question");
		questionTest2.setRequired(true);
		questionTest2.setRequiredComment(true);
		questionTest2.setAnswerType("yes|no");
		questionTest2.setQuestionChoices("value");
		listQuestions.add(questionTest2);
		
		questionTest3 = new Question();
		questionTest3.setText("text third question");
		questionTest3.setRequired(true);
		questionTest3.setRequiredComment(true);
		questionTest3.setAnswerType("yes|no");
		questionTest3.setQuestionChoices("value");
		listQuestions.add(questionTest3);
		
		groupQuestionsTest = new GroupQuestions();
		groupQuestionsTest.setTitle("Title");
		groupQuestionsTest.setQuestions(listQuestions);
		
		listGroupQuestions = new ArrayList<>();
		listGroupQuestions.add(groupQuestionsTest);
		surveyTest.setGroupQuestions(listGroupQuestions);
		
	}
	
	/**
	 * Test save survey to db
	 */
	@Test
	public void importTest() {
		
		surveyDAO.saveSurvey(surveyTest);
	}
}
