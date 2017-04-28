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

	private ArrayList<String> filterList = new ArrayList<String>();
	private static final Map<String, String> filtersMap = new HashMap<String, String>();

	static {
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

	public ArrayList<String> getFilterList() {
		return filterList;
	}

	public void setFilterList(ArrayList<String> filterList) {
		this.filterList = filterList;
	}

}