package com.softbistro.survey.statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.response.Response;
import com.softbistro.survey.statistic.component.service.StatisticDao;

@Service
public class StatisticService {
	@Autowired
	private StatisticDao statisticDao;

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	public Response surveyStatistic(Integer surveyId) {
		return statisticDao.surveyStatistic(surveyId);
	}
}
