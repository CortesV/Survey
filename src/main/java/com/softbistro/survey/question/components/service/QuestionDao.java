package com.softbistro.survey.question.components.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.question.components.interfaces.IQuestion;
import com.softbistro.survey.response.Response;

/**
 * CRUD for Question
 * 
 * @author cortes
 *
 */
@Repository
public class QuestionDao implements IQuestion {

	private static final String SELECT_QUESTION_BY_ID = "SELECT * FROM questions  WHERE id = ? AND `delete` = 0";
	private static final String SAVE_QUESTION = "INSERT INTO questions (survey_id, question, description_short, description_long, question_section_id, answer_type, "
			+ "question_choices, required) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUESTION = "UPDATE questions SET survey_id = ?, question = ?, description_short = ?, description_long = ?, question_section_id = ?, "
			+ "answer_type = ?, question_choices = ?, required = ? WHERE id = ?";

	private static final String DELETE_QUESTION = "UPDATE questions SET `delete` = 1 WHERE id = ?";

	@Autowired
	private JdbcTemplate jdbc;

	/**
	 * Class for geting clients from database
	 * 
	 * @author cortes
	 *
	 */
	public class WorkingWithRowMap implements RowMapper<Question> {

		@Override
		public Question mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Question question = new Question();
			question.setId(resultSet.getLong(1));
			question.setSurveyId(resultSet.getLong(2));
			question.setQuestion(resultSet.getString(3));
			question.setDescriptionShort(resultSet.getString(4));
			question.setDescriptionLong(resultSet.getString(5));
			question.setQuestionSectionId(resultSet.getLong(6));
			question.setAnswerType(resultSet.getString(7));
			question.setQuestionChoices(resultSet.getString(8));
			question.setRequired(resultSet.getBoolean(9));
			return question;
		}
	}

	/**
	 * Find question in database by id of question
	 * 
	 * @param id
	 *            id - id of question
	 * @return return - all information about question
	 */
	@Override
	public Response findQuestionById(Long id) {

		try {

			List<Question> questionList = jdbc.query(SELECT_QUESTION_BY_ID, new BeanPropertyRowMapper(Question.class),
					id);

			return questionList.isEmpty() ? new Response(null, HttpStatus.OK, null)
					: new Response(questionList.get(0), HttpStatus.OK, null);

		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

	}

	/**
	 * Save client to database
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	@Override
	public Response saveQuestion(Question question) {

		try {

			jdbc.update(SAVE_QUESTION, question.getSurveyId(), question.getQuestion(), question.getDescriptionShort(),
					question.getDescriptionLong(), question.getQuestionSectionId(), question.getAnswerType(),
					question.getQuestionChoices(), question.isRequired());
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.CREATED, null);

	}

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	@Override
	public Response deleteQuestion(Long id) {

		try {

			jdbc.update(DELETE_QUESTION, id);
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.OK, null);
	}

	/**
	 * Update information of question
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @param id
	 *            id-id of question
	 * @return return - status of execution this method
	 */
	@Override
	public Response updateQuestion(Question question, Long id) {

		try {
			jdbc.update(UPDATE_QUESTION, question.getSurveyId(), question.getQuestion(), question.getDescriptionShort(),
					question.getDescriptionLong(), question.getQuestionSectionId(), question.getAnswerType(),
					question.getQuestionChoices(), question.isRequired(), id);
		} catch (Exception ex) {

			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(null, HttpStatus.OK, null);

	}

}
