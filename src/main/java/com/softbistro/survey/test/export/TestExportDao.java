package com.softbistro.survey.test.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Data access object for testing dynamic export
 * 
 * @author af150416
 *
 */
@Repository
public class TestExportDao implements ITestExport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = Logger.getLogger(TestExportDao.class);

	private static final Map<String, String> pattern = new HashMap<String, String>();
	static {
		pattern.put("Survey ID", "survey_id");
		pattern.put("Survey Name", "survey_name");
		pattern.put("Participant First Name", "first_name");
		pattern.put("Participant Last Name", "last_name");
		pattern.put("Question Group Name", "section_name");
		pattern.put("Question Name", "question");
		pattern.put("Participant ID", "participant_id");
		pattern.put("Answer", "answer_value");
		pattern.put("Comment", "comment");
		pattern.put("Answer Date and Time", "answer_datetime");
		pattern.put("Participant Group", "group_name");
		pattern.put("Participant Attribute name", "attribute");
		pattern.put("Participant Attribute value", "attribute_value");
	}

	private static final String SQL_GET_STATISTIC_FOR_DYNAMIC_EXPORT = "SELECT survey.id AS survey_id, survey.`name` AS survey_name, p.first_name, p.last_name, "
			+ "question_sections.section_name AS section_name, questions.question, answers.participant_id, answers.answer_value, answers.`comment`, "
			+ "answers.modified_date AS answer_datetime, `group`.group_name, attributes.attribute, av.attribute_value FROM answers  LEFT JOIN questions "
			+ "ON answers.question_id = questions.id LEFT JOIN survey AS survey ON survey.id = questions.survey_id LEFT JOIN connect_question_section_survey "
			+ "AS cqss ON survey.id = cqss.survey_id LEFT JOIN question_sections ON question_sections.id = cqss.question_section_id LEFT JOIN participant "
			+ "AS p ON p.id = answers.participant_id LEFT JOIN connect_group_survey AS cgs ON cgs.survey_id = survey.id LEFT JOIN `group` ON "
			+ "`group`.id = cgs.group_id LEFT JOIN attributes ON attributes.group_id = `group`.id LEFT JOIN attribute_values AS av ON av.attribute_id = attributes.id "
			+ "WHERE survey.id= ? AND survey.delete=0 AND cqss.delete=0 AND question_sections.delete=0 AND p.delete=0 AND cgs.delete=0 AND `group`.delete=0 "
			+ "AND attributes.delete=0 AND av.delete=0";

	@Override
	public List<Map<String, Object>> testExport(List<String> filters, Integer id) {
		try {

			List<Map<String, Object>> export = jdbcTemplate.query(SQL_GET_STATISTIC_FOR_DYNAMIC_EXPORT,
					new ColumnMapRowMapper(), id);

			return export.isEmpty() ? null : export;
		}

		catch (Exception e) {

			LOGGER.error(e.getMessage());
			return null;
		}
	}

}
