package com.softbistro.survey.statistic.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.gdata.util.ServiceException;
import com.softbistro.survey.response.Response;
import com.softbistro.survey.statistic.export.ExportStatisticInSheets;
import com.softbistro.survey.statistic.export.SheetsService;
import com.softbistro.survey.statistic.export.test;
import com.softbistro.survey.statistic.service.StatisticService;

@RestController
@RequestMapping("rest/survey/v1/statistic")
public class StatisticController {

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private SheetsService sheetsService;

	@Autowired
	test t1;

	@Autowired
	private ExportStatisticInSheets exportStatisticInSheets;

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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public Spreadsheet exportStatistic() throws IOException {
		try {
			return sheetsService.create();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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

	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String exportSurveyStatistic1() {
		try {
			t1.start();
			// exportStatisticInSheets.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "DONE";
	}
}
