package com.softbistro.survey.statistic.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
import com.softbistro.survey.statistic.component.service.StatisticDao;
import com.softbistro.survey.statistic.export.SheetsService;

@Service
public class StatisticService {
	@Autowired
	private StatisticDao statisticDao;

	@Autowired
	private SheetsService sheetsService;

	private static final Logger LOG = Logger.getLogger(StatisticService.class);

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	public SurveyStatisticShort survey(Integer surveyId) {
		return statisticDao.survey(surveyId);
	}

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public String export(Integer surveyId, List<String> filters) {
		try {
			return sheetsService.send(statisticDao.export(surveyId),filters);
		} catch (GeneralSecurityException | IOException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
