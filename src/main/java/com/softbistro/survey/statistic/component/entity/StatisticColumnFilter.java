package com.softbistro.survey.statistic.component.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represent filter entity
 * 
 * @author af150416
 *
 */
public class StatisticColumnFilter {

	private static final ArrayList<String> filterList = new ArrayList<String>();
	private static final Map<String, String> filtersMap = new HashMap<String, String>();
	
	static {

		filterList.add("SurveyID");
		filterList.add("SurveyName");
		filterList.add("ParticipantFirstName");
		filterList.add("ParticipantLastName");
		filterList.add("QuestionGroupName");
		filterList.add("QuestionName");
		filterList.add("ParticipantID");
		filterList.add("Answer");
		filterList.add("Comment");
		filterList.add("AnswerDateAndTime");
		filterList.add("Attribute");


		filtersMap.put("SurveyID", "survey_id");
		filtersMap.put("SurveyName", "survey_name");
		filtersMap.put("ParticipantFirstName", "first_name");
		filtersMap.put("ParticipantLastName", "last_name");
		filtersMap.put("QuestionGroupName", "section_name");
		filtersMap.put("QuestionName", "question");
		filtersMap.put("ParticipantID", "participant_id");
		filtersMap.put("Answer", "answer_value");
		filtersMap.put("Comment", "comment");
		filtersMap.put("AnswerDateAndTime", "answer_datetime");
		filtersMap.put("Attribute", "attribute");

	}

	public static Map<String, String> getFiltersMap() {
		return filtersMap;
	}

	public static ArrayList<String> getFilterlist() {
		return filterList;
	}
}