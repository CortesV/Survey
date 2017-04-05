package com.softbistro.survey.statistic.component.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.statistic.component.entity.ParticipantAttributes;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
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

	private static final String SQL_GET_STATISTIC_FOR_EXPORT = "SELECT  s.id AS survey_id, s.`name` AS survey_name, p.first_name, p.last_name, question_sections.section_name AS group_name,"
			+ "questions.question, answers.participant_id ,  answers.answer_value, answers.comment, answers.modified_date AS answer_datetime "
			+ "FROM answers  LEFT JOIN questions ON answers.question_id = questions.id "
			+ "LEFT JOIN survey AS s ON s.id = questions.survey_id "
			+ "LEFT JOIN connect_question_section_survey ON s.id = connect_question_section_survey.survey_id "
			+ "LEFT JOIN question_sections ON question_sections.id = connect_question_section_survey.question_section_id "
			+ "LEFT JOIN participant AS p ON p.id = answers.participant_id WHERE s.id= ?";

	private static final String SQL_GET_PARTICIPANT_ATTRIBUTES = "SELECT attribute, attribute_value FROM attributes "
			+ "INNER JOIN attribute_values ON attributes.id = attribute_values.attribute_id "
			+ "WHERE participant_id = ?";

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	@Override
	public ResponseEntity<SurveyStatisticShort> surveyStatistic(Integer surveyId) {
		SurveyStatisticShort surveyStatisticShort = jdbcTemplate.queryForObject(SQL_GET_SURVEY_STATISTIC_SHORT,
				new RowMapper<SurveyStatisticShort>() {

					@Override
					public SurveyStatisticShort mapRow(ResultSet rs, int rowNum) throws SQLException {
						SurveyStatisticShort shortSurvey = new SurveyStatisticShort();
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

		return new ResponseEntity<SurveyStatisticShort>(surveyStatisticShort, HttpStatus.OK);
	}

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@Override
	public List<SurveyStatisticExport> exportSurveyStatistic(Integer surveyId) {
		List<SurveyStatisticExport> surveyStatisticExport = new ArrayList<>();

		surveyStatisticExport = jdbcTemplate.query(SQL_GET_STATISTIC_FOR_EXPORT,
				new RowMapper<SurveyStatisticExport>() {

					@Override
					public SurveyStatisticExport mapRow(ResultSet rs, int rowNum) throws SQLException {
						SurveyStatisticExport surveyStatisticExport = new SurveyStatisticExport();

						surveyStatisticExport.setId(rs.getInt(1));
						surveyStatisticExport.setName(rs.getString(2));
						surveyStatisticExport.setFirstName(rs.getString(3));
						surveyStatisticExport.setLastName(rs.getString(4));
						surveyStatisticExport.setGroupName(rs.getString(5));
						surveyStatisticExport.setQuestionName(rs.getString(6));
						surveyStatisticExport.setParticipantId(rs.getInt(7));
						surveyStatisticExport.setAnswer(rs.getString(8));
						surveyStatisticExport.setComment(rs.getString(9));
						surveyStatisticExport.setAnswerDateTime(rs.getDate(10));

						surveyStatisticExport.setParticipantAttribute(jdbcTemplate.query(SQL_GET_PARTICIPANT_ATTRIBUTES,
								new RowMapper<ParticipantAttributes>() {

									@Override
									public ParticipantAttributes mapRow(ResultSet rs, int rowNum) throws SQLException {
										ParticipantAttributes participantAttributes = new ParticipantAttributes();
										participantAttributes.setName(rs.getString(1));
										participantAttributes.setValue(rs.getString(2));
										return participantAttributes;
									}

								}, rs.getInt(7)));

						return surveyStatisticExport;
					}
				}, surveyId);

		return surveyStatisticExport;
	}

}
