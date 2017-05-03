package com.survey.softbistro.startapp;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import com.softbistro.survey.creating.survey.component.service.SurveyDao;
import com.softbistro.survey.imports.system.components.entities.ImportSurvey;
import com.softbistro.survey.imports.system.components.interfaces.IImportSurveyDAO;
import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Import survey in csv file
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
public class ImportIntegrationTest {

	@Autowired
	private IImportSurveyDAO iImportSurveyDao;

	@Autowired
	private SurveyDao surveyDao;

	private String questionName = "TestQuestion";

	private String groupName = "TestGroup";

	private Integer groupId = 2;

	private Integer clientId = Integer.MAX_VALUE;

	private String surveyName = "TestSurveyName";

	private Integer generatedSurveyId;

	private String surveyTitle = "TestTitle";

	private String questionDescriptionShort = "testShortDescription";

	private String questionDescriptionLong = "testLongDescription";

	private Integer questionSectionId = 2;

	private String answerType = "RATE1-10";

	private String questionChoices = "TestChoice";

	private boolean required = true;

	private boolean requiredComment = true;

	@Before
	public void setUp() {

		Question question = new Question();
		question.setQuestion(questionName);
		question.setDescriptionShort(questionDescriptionShort);
		question.setDescriptionLong(questionDescriptionLong);
		question.setQuestionSectionId(questionSectionId);
		question.setAnswerType(answerType);
		question.setQuestionChoices(questionChoices);
		question.setRequired(required);
		question.setRequiredComment(requiredComment);
		question.setGroupName(groupName);

		List<Question> questions = new LinkedList<>();
		questions.add(0, question);

		Map<String, Integer> groups = new HashMap<>();
		groups.put(groupName, groupId);

		ImportSurvey importSurvey = new ImportSurvey();
		importSurvey = new ImportSurvey();
		importSurvey.setGroups(groups);
		importSurvey.setQuestions(questions);
		importSurvey.setTitle(surveyName);
		importSurvey.setClienId(clientId);
		importSurvey.setStartTime(new Date(System.currentTimeMillis()));
		importSurvey.setFinishTime(new Date(System.currentTimeMillis()));
		importSurvey.setTitle(surveyTitle);

		generatedSurveyId = iImportSurveyDao.saveSurvey(importSurvey);
	}

	/**
	 * Test save survey to db
	 */
	@Test
	public void saveSurveyTest() {
		assertNotSame(generatedSurveyId, 0);
		assertNotEquals(surveyDao.getAllSurveysByClient(clientId).size(), 0);

	}
}
