package com.softbistro.survey.statistic.component.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.response.Response;
import com.softbistro.survey.statistic.component.entity.SurveyStatistikShort;
import com.softbistro.survey.statistic.component.interfacee.IStatistic;

/**
 * Working with database for statistic
 * 
 * @author zviproject
 *
 */
@Repository
public class StatisticDao implements IStatistic {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_GET_SURVEY_STATISTIC_SHORT = "SELECT s.id, s.name, s.start_time, s.finish_time, COUNT(ss.id) AS participant_count ,"
			+ " (SELECT COUNT(ss.id) AS voted_count  FROM sending_survey AS ss "
			+ "WHERE ss.answer_status = 'VOTED' AND ss.survey_id = ? ) AS voted_count "
			+ "FROM survey AS s , sending_survey AS ss WHERE s.id = ? AND ss.survey_id = ?";

	private static final String SQL_GET_COUNT_VOTES_OF_SURVEY = "SELECT COUNT(ss.id) AS voted_count  FROM sending_survey AS ss "
			+ "WHERE ss.answer_status = 'VOTED' AND ss.survey_id = ?";

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	@Override
	public Response surveyStatistic(Integer surveyId) {
		SurveyStatistikShort surveyStatistikShort = jdbcTemplate.queryForObject(SQL_GET_SURVEY_STATISTIC_SHORT,
				new RowMapper<SurveyStatistikShort>() {

					@Override
					public SurveyStatistikShort mapRow(ResultSet rs, int rowNum) throws SQLException {
						SurveyStatistikShort shortSurvey = new SurveyStatistikShort();
						shortSurvey.setId(rs.getInt(1));
						shortSurvey.setName(rs.getString(2));
						shortSurvey.setStartTimeOfSurvey(rs.getDate(3));
						shortSurvey.setFinishTimeOfSurvey(rs.getDate(4));
						shortSurvey.setParticipantCount(rs.getInt(5));
						shortSurvey.setParticipantVoted(rs.getInt(6));

						shortSurvey.setParticipanNotVoted(rs.getInt(5) - rs.getInt(6));
						return shortSurvey;
					}

				}, surveyId, surveyId, surveyId);

		return new Response(surveyStatistikShort, HttpStatus.OK, null);
	}

}
