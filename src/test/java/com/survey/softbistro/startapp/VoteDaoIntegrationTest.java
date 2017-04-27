package com.survey.softbistro.startapp;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
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

import com.softbistro.survey.confirm.url.component.entity.Answer;
import com.softbistro.survey.confirm.url.component.entity.VotePage;
import com.softbistro.survey.confirm.url.component.service.VoteDao;
import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.question.components.service.QuestionDao;
import com.softbistro.survey.standalone.SurveySoftBistroApplication;

/**
 * Test VoteDao class
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
public class VoteDaoIntegrationTest {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private VoteDao voteDao;

	private static final String UUID_FOR_TEST = "51695b96-21df-4c95-9585-222cdec18556";

	private static final Integer SURVEY_ID = 1;

	private String questionName = "TestQuestion";

	private String questionDescriptionShort = "testShortDescription";

	private String questionDescriptionLong = "testLongDescription";

	private Integer questionSectionId = 2;

	private String answerType = "RATE1-10";

	private String questionChoices = "TestChoice";

	private boolean required = true;

	private boolean requiredComment = true;

	public String answerValue = "testAnswer";

	private Question question;

	private Integer questionId;

	/**
	 * Prepare data for test
	 */
	@Before
	public void insertData() {
		question = new Question();
		question.setSurveyId(SURVEY_ID);
		question.setQuestion(questionName);
		question.setDescriptionShort(questionDescriptionShort);
		question.setDescriptionLong(questionDescriptionLong);
		question.setQuestionSectionId(questionSectionId);
		question.setAnswerType(answerType);
		question.setQuestionChoices(questionChoices);
		question.setRequired(required);
		question.setRequiredComment(requiredComment);
		questionId = questionDao.saveQuestion(question);
	}

	/**
	 * Get page for vote by url
	 */
	@Test
	public void getVotePageTest() {
		List<VotePage> votePages = voteDao.getVotePage(UUID_FOR_TEST);
		assertEquals(votePages.size(), 1);

	}

	/**
	 * Answer on question
	 */
	@Test
	public void answerOnQuestion() {
		Answer answer = new Answer();

		answer.setAnswerValue(answerValue);
		answer.setQuestionId(questionId);

		List<Answer> answers = new LinkedList<>();
		answers.add(answer);

		voteDao.answerOnSurvey(UUID_FOR_TEST, answers);

	}

}
