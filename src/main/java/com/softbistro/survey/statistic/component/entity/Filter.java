package com.softbistro.survey.statistic.component.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represent filter entity
 * 
 * @author af150416
 *
 */
public class Filter {

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

	public static Map<String, String> getPattern() {
		return pattern;
	}
}
