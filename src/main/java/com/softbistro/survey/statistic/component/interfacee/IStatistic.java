package com.softbistro.survey.statistic.component.interfacee;

import com.softbistro.survey.response.Response;

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
	public Response surveyStatistic(Integer surveyId);
}
