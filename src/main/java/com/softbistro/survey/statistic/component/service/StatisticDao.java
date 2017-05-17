package com.softbistro.survey.statistic.component.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.statistic.component.entity.StatisticColumnFilter;
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

	private static final String SQL_GET_STATISTIC_FOR_EXPORT = "SELECT DISTINCT answers.id AS answer_id, survey.id AS survey_id, survey.`name` AS survey_name, p.first_name, p.last_name, "
			+ "question_sections.section_name AS section_name, questions.question, answers.participant_id, answers.answer_value, answers.`comment`, "
			+ "answers.modified_date AS answer_datetime, `group`.group_name, attributes.attribute, av.attribute_value FROM answers  LEFT JOIN questions "
			+ "ON answers.question_id = questions.id LEFT JOIN survey AS survey ON survey.id = questions.survey_id LEFT JOIN connect_question_section_survey "
			+ "AS cqss ON survey.id = cqss.survey_id LEFT JOIN question_sections ON question_sections.id = cqss.question_section_id LEFT JOIN participant "
			+ "AS p ON p.id = answers.participant_id LEFT JOIN connect_group_survey AS cgs ON cgs.survey_id = survey.id LEFT JOIN `group` ON "
			+ "`group`.id = cgs.group_id LEFT JOIN attributes ON attributes.group_id = `group`.id LEFT JOIN attribute_values AS av ON av.attribute_id = attributes.id AND av.participant_id = p.id "
			+ "WHERE survey.id= ? AND survey.delete=0 AND cqss.delete=0 AND question_sections.delete=0 AND p.delete=0 AND cgs.delete=0 AND `group`.delete=0 "
			+ "AND attributes.delete=0 AND av.delete=0";

	private static final String SQL_GET_STATISTIC_FILTERS_FOR_EXPORT = "SELECT DISTINCT `group`.group_name, attributes.attribute FROM answers INNER JOIN questions "
			+ "ON answers.question_id = questions.id INNER JOIN survey AS survey ON survey.id = questions.survey_id INNER JOIN connect_question_section_survey AS cqss "
			+ "ON survey.id = cqss.survey_id INNER JOIN question_sections ON question_sections.id = cqss.question_section_id INNER JOIN participant AS p "
			+ "ON p.id = answers.participant_id INNER JOIN connect_group_survey AS cgs ON cgs.survey_id = survey.id INNER JOIN `group` ON `group`.id = cgs.group_id "
			+ "INNER JOIN attributes ON attributes.group_id = `group`.id INNER JOIN attribute_values AS av ON (av.attribute_id = attributes.id AND av.participant_id = p.id) "
			+ "WHERE survey.id= ? AND survey.delete=0 AND cqss.delete=0 AND question_sections.delete=0 AND p.delete=0 AND cgs.delete=0 AND `group`.delete=0 "
			+ "AND attributes.delete=0 AND av.delete=0";

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
	public List<Map<String, Object>> export(Integer surveyId) {
		try {
			Optional<List<Map<String, Object>>> export = Optional
					.ofNullable(jdbcTemplate.query(SQL_GET_STATISTIC_FOR_EXPORT, new ColumnMapRowMapper(), surveyId));

			return export.orElse(null);
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
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

		try {

			List<Map<String, Object>> rawFilters = jdbcTemplate.query(SQL_GET_STATISTIC_FILTERS_FOR_EXPORT,
					new ColumnMapRowMapper(), surveyId);

			for (int count = 0; count < rawFilters.size(); count++) {

				String newFilter = (rawFilters.get(count).get("group_name").toString()
						+ rawFilters.get(count).get("attribute").toString()).replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
				if (!filters.contains(newFilter)) {

					filters.add(newFilter);
				}
			}
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
		}

		return filters;
	}
}
