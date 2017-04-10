package com.softbistro.survey.question.components.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.question.components.interfaces.IQuestion;

/**
 * CRUD for Question
 * 
 * @author cortes
 *
 */
@Repository
public class QuestionDao implements IQuestion {

	private static final Logger LOGGER = Logger.getLogger(QuestionDao.class);

	private static final String SELECT_QUESTION_BY_ID = "SELECT * FROM questions  WHERE id = ? AND `delete` = 0";
	private static final String SAVE_QUESTION = "INSERT INTO questions (survey_id, question, description_short, description_long, question_section_id, answer_type, "
			+ "question_choices, required, required_comment) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUESTION = "UPDATE questions SET survey_id = ?, question = ?, description_short = ?, description_long = ?, question_section_id = ?, "
			+ "answer_type = ?, question_choices = ?, required = ?, required_comment = ?  WHERE id = ?";
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
			question.setId(resultSet.getInt(1));
			question.setSurveyId(resultSet.getInt(2));
			question.setQuestion(resultSet.getString(3));
			question.setDescriptionShort(resultSet.getString(4));
			question.setDescriptionLong(resultSet.getString(5));
			question.setQuestionSectionId(resultSet.getInt(6));
			question.setAnswerType(resultSet.getString(7));
			question.setQuestionChoices(resultSet.getString(8));
			question.setRequired(resultSet.getBoolean(9));
			question.setRequiredComment(resultSet.getBoolean(10));
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
	public Question findQuestionById(Integer id) {

		try {

			List<Question> questionList = jdbc.query(SELECT_QUESTION_BY_ID, new BeanPropertyRowMapper<>(Question.class),
					id);

			return questionList.isEmpty() ? null : questionList.get(0);

		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
			return null;
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
	public Integer saveQuestion(Question question) {

		try {

			Connection connection = jdbc.getDataSource().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUESTION,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, question.getSurveyId());
			preparedStatement.setString(2, question.getQuestion());
			preparedStatement.setString(3, question.getDescriptionShort());
			preparedStatement.setString(4, question.getDescriptionLong());
			preparedStatement.setInt(5, question.getQuestionSectionId());
			preparedStatement.setString(6, question.getAnswerType());
			preparedStatement.setString(7, question.getQuestionChoices());
			preparedStatement.setBoolean(8, question.isRequired());
			preparedStatement.setBoolean(9, question.isRequiredComment());

			preparedStatement.executeUpdate();
			ResultSet keys = preparedStatement.getGeneratedKeys();

			Integer generatedId = 0;
			if (keys.next()) {
				generatedId = keys.getInt(1);
			}

			return generatedId;

			/*
			 * jdbc.update(SAVE_QUESTION, question.getSurveyId(),
			 * question.getQuestion(), question.getDescriptionShort(),
			 * question.getDescriptionLong(), question.getQuestionSectionId(),
			 * question.getAnswerType(), question.getQuestionChoices(),
			 * question.isRequired(), question.isRequiredComment());
			 */
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
			return null;
		}

	}

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	@Override
	public void deleteQuestion(Integer id) {

		try {

			jdbc.update(DELETE_QUESTION, id);
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
		}

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
	public void updateQuestion(Question question, Integer id) {

		try {
			jdbc.update(UPDATE_QUESTION, question.getSurveyId(), question.getQuestion(), question.getDescriptionShort(),
					question.getDescriptionLong(), question.getQuestionSectionId(), question.getAnswerType(),
					question.getQuestionChoices(), question.isRequired(), question.isRequiredComment(), id);
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
		}

	}

}
