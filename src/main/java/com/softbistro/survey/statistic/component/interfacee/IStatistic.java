package com.softbistro.survey.statistic.component.interfacee;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;

/**
 * Methods for workng with statistic
 * 
 * @author zviproject
 *
 */
public interface IStatistic {

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	public ResponseEntity<SurveyStatisticShort> surveyStatistic(Integer surveyId);

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public List<SurveyStatisticExport> exportSurveyStatistic(Integer surveyId);
}
