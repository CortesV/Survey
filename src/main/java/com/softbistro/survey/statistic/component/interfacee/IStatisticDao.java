package com.softbistro.survey.statistic.component.interfacee;

import java.util.List;

import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
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
	public List<SurveyStatisticExport> export(Integer surveyId);
}
