package com.softbistro.survey.statistic.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
import com.softbistro.survey.statistic.component.service.StatisticDao;
import com.softbistro.survey.statistic.export.SheetsService;

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
	public ResponseEntity<SurveyStatisticShort> surveyStatistic(Integer surveyId) {
		return statisticDao.surveyStatistic(surveyId);
	}

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public ResponseEntity<Object> exportSurveyStatistic(Integer surveyId) {
		// try {
		SheetsService sheetsService = new SheetsService();

		try {
			sheetsService.send(statisticDao.exportSurveyStatistic(surveyId));
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(statisticDao.exportSurveyStatistic(surveyId), HttpStatus.OK);
		// } catch (Exception e) {
		// return new ResponseEntity<Object>(e.getMessage(),
		// HttpStatus.INTERNAL_SERVER_ERROR);
		// }

	}
}
