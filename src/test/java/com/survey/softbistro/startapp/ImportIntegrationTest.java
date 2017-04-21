package com.survey.softbistro.startapp;

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
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Integration test for import survey
 * 
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

	}

	/**
	 * Test save survey to db
	 */
	@Test
	public void importTest() {

		System.out.println("Hello");
	}
}
