package com.softbistro.survey.statistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.response.Response;
import com.softbistro.survey.statistic.service.StatisticService;

@RestController
@RequestMapping("rest/survey/v1/statistic")
public class StatisticController {

	@Autowired
	private StatisticService statisticService;

	@RequestMapping(value = "/{survey_id}")
	public Response surveyStatistic(@PathVariable(value = "survey_id") Integer surveyId) {
		return statisticService.surveyStatistic(surveyId);
	}
}
