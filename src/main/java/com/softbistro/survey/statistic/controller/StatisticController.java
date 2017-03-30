package com.softbistro.survey.statistic.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.response.Response;
import com.softbistro.survey.statistic.export.GoogleSheetsService;
import com.softbistro.survey.statistic.service.StatisticService;

@RestController
@RequestMapping("rest/survey/v1/statistic")
public class StatisticController {

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private GoogleSheetsService googleSheetsService;

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	@RequestMapping(value = "/{survey_id}", method = RequestMethod.GET)
	public Response surveyStatistic(@PathVariable(value = "survey_id") Integer surveyId) {
		return statisticService.surveyStatistic(surveyId);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String exportStatistic() throws IOException {
		googleSheetsService.testing();
		return "DONE";
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
