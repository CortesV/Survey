package com.softbistro.survey.statistic.component.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.statistic.component.entity.ParticipantAttributes;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;

/**
 * Working with database to get statistic data
 * 
 * @author alex_alokhin
 *
 */
@Repository
public class GeneralStatisticDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_GET_STATISTIC_FOR_EXPORT = "SELECT  survey.id AS survey_id, survey.`name` AS survey_name, p.first_name, p.last_name, question_sections.section_name AS group_name,"
			+ "questions.question, answers.participant_id ,  answers.answer_value, answers.comment, answers.modified_date AS answer_datetime "
			+ "FROM answers  LEFT JOIN questions ON answers.question_id = questions.id "
			+ "LEFT JOIN survey AS survey ON survey.id = questions.survey_id "
			+ "LEFT JOIN connect_question_section_survey ON survey.id = connect_question_section_survey.survey_id "
			+ "LEFT JOIN question_sections ON question_sections.id = connect_question_section_survey.question_section_id "
			+ "LEFT JOIN participant AS p ON p.id = answers.participant_id WHERE survey.id IS NOT NULL";

	private static final String SQL_GET_PARTICIPANT_ATTRIBUTES = "SELECT attribute, attribute_value FROM attributes "
			+ "INNER JOIN attribute_values ON attributes.id = attribute_values.attribute_id WHERE participant_id = ?";

	/**
	 * Get statistic about surveys
	 * @return  - list of surveys statistic 
	 */
	public List<SurveyStatisticExport> getAllStatistic(){
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
			});
		
		return surveyStatisticExport;
	}
}
