package com.softbistro.survey.statistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.response.Response;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
import com.softbistro.survey.statistic.export.SheetsService;
import com.softbistro.survey.statistic.service.StatisticService;

@RestController
@RequestMapping("rest/survey/v1/statistic")
public class StatisticController {

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private SheetsService sheetsService;

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	@RequestMapping(value = "/{survey_id}", method = RequestMethod.GET)
	public ResponseEntity<SurveyStatisticShort> surveyStatistic(@PathVariable(value = "survey_id") Integer surveyId) {
		return statisticService.surveyStatistic(surveyId);
	}

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/{survey_id}/get")
	public Response exportSurveyStatistic(@PathVariable("survey_id") Integer surveyId) {
		return statisticService.exportSurveyStatistic(surveyId);
	}

}
