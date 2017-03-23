package com.softbistro.survey.confirm.survey.component.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.confirm.survey.component.entity.Answer;
import com.softbistro.survey.confirm.survey.component.entity.UuidInformation;
import com.softbistro.survey.confirm.survey.component.interfacee.IVote;
import com.softbistro.survey.response.Response;

@Repository
public class VoteDao implements IVote {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_GET_UUID_INFORMATION = "SELECT survey_id, participant_id FROM sending_survey WHERE url=? AND working_time > ?";

	private static final String SQL_INSERT_ANSWERS = "INSERT INTO answers (question_id, participant_id, answer_type, answer_value, `comment`) VALUES(?,?,?,?,?)";

	private class GetInformationFromUuid implements RowMapper<UuidInformation> {

		@Override
		public UuidInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
			UuidInformation uuidInformation = new UuidInformation();
			uuidInformation.setPartisipantId(rs.getInt(1));
			uuidInformation.setQuestionId(rs.getInt(2));

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
	public Response answerOnSurvey(String uuid, List<Answer> answers) {
		try {
			Date date = new Date(System.currentTimeMillis());
			UuidInformation uuidInformation = jdbcTemplate.queryForObject(SQL_GET_UUID_INFORMATION,
					new GetInformationFromUuid(), uuid, date);

			jdbcTemplate.batchUpdate(SQL_INSERT_ANSWERS, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Answer answer = answers.get(i);
					ps.setInt(1, uuidInformation.getQuestionId());
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
			return new Response(null, HttpStatus.OK, null);
		} catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
