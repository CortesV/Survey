package com.softbistro.survey.statistic.component.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.statistic.component.entity.ParticipantAttributes;
import com.softbistro.survey.statistic.component.entity.StatisticColumnFilter;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShortMapper;
import com.softbistro.survey.statistic.component.interfacee.IStatisticDao;

/**
 * Working with database for statistic
 * 
 * @author zviproject
 *
 */
@Repository
public class StatisticDao implements IStatisticDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StatisticColumnFilter statisticColumnFilter;

	private static final Logger LOGGER = Logger.getLogger(StatisticDao.class);

	private static final String SQL_GET_SURVEY_STATISTIC_SHORT = "SELECT survey.id, survey.name, survey.start_time, survey.finish_time, COUNT(ss.id) AS participant_count ,"
			+ " (SELECT COUNT(ss.id) AS voted_count  FROM sending_survey AS ss "
			+ "WHERE ss.answer_status = 'VOTED' AND ss.survey_id = ? ) AS voted_count "
			+ "FROM survey AS survey , sending_survey AS ss WHERE survey.id = ? AND ss.survey_id = ?";

	private static final String SQL_GET_STATISTIC_FOR_EXPORT = "SELECT DISTINCT survey.id AS survey_id, survey.`name` AS survey_name, p.first_name, p.last_name, "
			+ "question_sections.section_name AS section_name, questions.question, answers.participant_id, answers.answer_value, answers.`comment`, answers.modified_date "
			+ "AS answer_datetime FROM survey.answers LEFT JOIN questions ON answers.question_id = questions.id LEFT JOIN survey AS survey ON survey.id = questions.survey_id "
			+ "LEFT JOIN connect_question_section_survey AS cqss ON survey.id = cqss.survey_id LEFT JOIN question_sections ON question_sections.id = cqss.question_section_id "
			+ "LEFT JOIN participant AS p ON p.id = answers.participant_id LEFT JOIN connect_group_survey AS cgs ON cgs.survey_id = survey.id WHERE survey.id= ? "
			+ "AND survey.delete=0 AND cqss.delete=0 AND question_sections.delete=0 AND p.delete=0 AND cgs.delete=0";

	private static final String SQL_GET_PARTICIPANT_ATTRIBUTE_VALUES = "SELECT `group`.group_name, attribute, attribute_value FROM attributes INNER JOIN attribute_values "
			+ "ON attributes.id = attribute_values.attribute_id INNER JOIN `group` ON `group`.id = attributes.group_id WHERE participant_id = ? "
			+ "AND `group`.delete = 0 AND attributes.delete = 0 AND attribute_values.delete = 0";

	private static final String SQL_GET_PARTICIPANT_ATTRIBUTES = "SELECT DISTINCT `group`.group_name, attributes.attribute FROM attributes INNER JOIN connect_group_survey AS cgs "
			+ "ON cgs.group_id = attributes.group_id INNER JOIN `group` ON `group`.id = cgs.group_id WHERE cgs.survey_id = ? AND cgs.delete=0 AND attributes.delete=0 AND `group`.delete = 0";

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	@Override
	public SurveyStatisticShort survey(Integer surveyId) {
		SurveyStatisticShort surveyStatisticShort = jdbcTemplate.queryForObject(SQL_GET_SURVEY_STATISTIC_SHORT,
				new SurveyStatisticShortMapper(), surveyId, surveyId, surveyId);

		return surveyStatisticShort;
	}

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@Override
	public List<SurveyStatisticExport> export(Integer surveyId) {

		List<SurveyStatisticExport> surveyStatisticExport = jdbcTemplate.query(SQL_GET_STATISTIC_FOR_EXPORT,
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

						surveyStatisticExport.setParticipantAttribute(jdbcTemplate
								.query(SQL_GET_PARTICIPANT_ATTRIBUTE_VALUES, new RowMapper<ParticipantAttributes>() {

									@Override
									public ParticipantAttributes mapRow(ResultSet rs, int rowNum) throws SQLException {
										ParticipantAttributes participantAttributes = new ParticipantAttributes();
										participantAttributes.setName(rs.getString(1) + rs.getString(2));
										participantAttributes.setValue(rs.getString(3));
										return participantAttributes;
									}
								}, rs.getInt(7)));
						return surveyStatisticExport;
					}
				}, surveyId);

		return surveyStatisticExport;
	}

	/**
	 * Get Statistic Filters for Export statistic on google sheets
	 * 
	 * @param surveyId
	 * 
	 * @param surveyId
	 * @return statisticColumnFilter
	 */
	@Override
	public List<String> getStatisticColumnFilters(Integer surveyId) {

		List<String> filters = statisticColumnFilter.getFilterList();
		List<Map<String, Object>> attributes = jdbcTemplate.query(SQL_GET_PARTICIPANT_ATTRIBUTES,
				new ColumnMapRowMapper(), surveyId);
		attributes.stream()
				.forEach(item -> filters.add((item.get("group_name").toString() + item.get("attribute").toString())
						.replaceAll("[^\\p{Alpha}\\p{Digit}]+", "")));

		return filters;
	}
}
