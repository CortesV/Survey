package com.softbistro.survey.confirm.url.component.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.cj.mysqlx.protobuf.MysqlxDatatypes.Array;
import com.softbistro.survey.confirm.url.component.entity.Answer;
import com.softbistro.survey.confirm.url.component.entity.UuidInformation;
import com.softbistro.survey.confirm.url.component.entity.VotePage;
import com.softbistro.survey.confirm.url.component.interfacee.IVote;

@Repository
public class VoteDao implements IVote {
	
	private static final Logger LOGGER = Logger.getLogger(VoteDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_GET_UUID_INFORMATION = "SELECT participant_id, survey_id FROM sending_survey WHERE url=? AND working_time > ? AND answer_status='NEW'";

	private static final String SQL_INSERT_ANSWERS = "INSERT INTO answers (question_id, participant_id, answer_type, answer_value, `comment`) VALUES(?,?,?,?,?)";

	private static final String SQL_UPDATE_STATUS_SENDING_SURVEY = "UPDATE sending_survey SET answer_status= ? WHERE url = ?";

	private static final String SQL_GET_INFORMATION_ABOUT_QUESTIONS = "SELECT question, required, question_choices, answer_type, required_comment, id FROM questions "
			+ "WHERE survey_id = ? AND `delete` = 0";

	private final String statusForUpdate = "VOTED";

	private class GetInformationFromUuid implements RowMapper<UuidInformation> {

		@Override
		public UuidInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
			UuidInformation uuidInformation = new UuidInformation();
			uuidInformation.setPartisipantId(rs.getInt(1));
			uuidInformation.setSurveyId(rs.getInt(2));

			return uuidInformation;
		}

	}

	/**
	 * Writing answers to questions in the database
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public void answerOnSurvey(String uuid, List<Answer> answers) {
		
		try {
			
			Date date = new Date(System.currentTimeMillis());
			UuidInformation uuidInformation = jdbcTemplate.queryForObject(SQL_GET_UUID_INFORMATION,
					new GetInformationFromUuid(), uuid, date);

			jdbcTemplate.batchUpdate(SQL_INSERT_ANSWERS, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					
					Answer answer = answers.get(i);
					ps.setInt(1, answer.getQuestionId());
					ps.setInt(2, uuidInformation.getPartisipantId());
					ps.setString(3, answer.getAnswerType());
					ps.setString(4, answer.getAnswerValue());
					ps.setString(5, answer.getComment());

				}

				@Override
				public int getBatchSize() {
					
					return answers.size();
				}
			});

			jdbcTemplate.update(SQL_UPDATE_STATUS_SENDING_SURVEY, statusForUpdate, uuid);

		} catch (Exception e) {
			
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Response for site with information about questions
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public List<VotePage> getVotePage(String uuid) {
		
		try {
			
			Date date = new Date(System.currentTimeMillis());
			UuidInformation uuidInformation = jdbcTemplate.queryForObject(SQL_GET_UUID_INFORMATION,
					new GetInformationFromUuid(), uuid, date);

			List<VotePage> votePages = jdbcTemplate.query(SQL_GET_INFORMATION_ABOUT_QUESTIONS,
					new RowMapper<VotePage>() {

						@Override
						public VotePage mapRow(ResultSet rs, int rowNum) throws SQLException {			
							
							return getQuestionsInfo(rs);
						}

					}, uuidInformation.getSurveyId());
			return votePages;
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	private VotePage getQuestionsInfo(ResultSet rs) throws SQLException{
		
		
		VotePage votePage = new VotePage();

		List<String> choiseForQuestion = new ArrayList<>();

		String allChoiseForQuestion = rs.getString(3);
		
		Arrays.asList(allChoiseForQuestion.split("/")).stream().forEach(retval -> choiseForQuestion.add(retval));
		
		votePage.setQuestionName(rs.getString(1));
		votePage.setRequired(rs.getInt(2));
		votePage.setQuestionAnswers(choiseForQuestion);
		votePage.setAnswerType(rs.getString(4));
		votePage.setRequiredComment(rs.getInt(5));
		votePage.setId(rs.getInt(6));
		
		return votePage;
	}
}
