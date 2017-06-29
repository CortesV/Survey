package com.softbistro.survey.statistic.component.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Class that represent filter entity
 * 
 * @author af150416
 *
 */
@Component
public class StatisticColumnFilter {

	private static final List<String> filterList = new ArrayList<String>();

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
	}

	public static List<String> getFilterList() {
		return filterList;
	}
}