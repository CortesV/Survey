package com.softbistro.survey.statistic.component.interfacee;

import java.util.List;
import java.util.Map;

import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;

/**
 * Methods for workng with statistic
 * 
 * @author zviproject
 *
 */
public interface IStatisticDao {

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	public SurveyStatisticShort survey(Integer surveyId);

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public List<Map<String, Object>> export(Integer surveyId);

	/**
	 * Get Statistic Filters for Export statistic on google sheets
	 * 
	 * @param
	 * @return statisticColumnFilter
	 */
	public List<String> getStatisticColumnFilters();
}
