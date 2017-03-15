package com.survey.softbistro.question.components.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.survey.softbistro.client.manage.components.entity.Client;
import com.survey.softbistro.client.manage.components.service.ClientDao.WorkingWithRowMap;
import com.survey.softbistro.question.components.entity.Question;
import com.survey.softbistro.question.components.interfaces.IQuestion;
import com.survey.softbistro.response.Response;

/**
 * CRUD for Question
 * 
 * @author cortes
 *
 */
@Repository
public class QuestionDao implements IQuestion {

	private static final String SELECT_QUESTION_BY_ID = "SELECT * FROM survey.questions  WHERE id = ?";
	private static final String SAVE_QUESTION = "INSERT INTO survey.questions (survey_id, question, description_short, description_long, question_section_id, answer_type, "
			+ "question_choices, required) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUESTION = "UPDATE survey.questions SET survey_id = ?, question = ?, description_short = ?, description_long = ?, question_section_id = ?, answer_type = ?, question_choices = ?, required = ? WHERE id = ?";
	private static final String DELETE_QUESTION = "DELETE FROM survey.questions WHERE id = ?";

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

		Question question = new Question();
		try {

			question = jdbc.queryForObject(SELECT_QUESTION_BY_ID, new WorkingWithRowMap(), id);
		} catch (Exception ex) {

			return new Response(question, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return new Response(question, HttpStatus.OK, null);

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
