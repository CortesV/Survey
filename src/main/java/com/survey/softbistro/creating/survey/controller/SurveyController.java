package com.survey.softbistro.creating.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.survey.softbistro.creating.survey.component.entity.Group;
import com.survey.softbistro.creating.survey.component.entity.Survey;
import com.survey.softbistro.creating.survey.service.SurveyService;
import com.survey.softbistro.response.Response;
import com.survey.softbistro.response.Status;

import net.minidev.json.JSONArray;

@RestController
@RequestMapping(value = "rest/survey/v1/survey")
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response create(@RequestBody Survey survey) {
		return surveyService.createSurvey(survey);
	}

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	@RequestMapping(value = "/survey_{survey_id}/update", method = RequestMethod.PUT)
	public Response update(@RequestHeader(name = "new_name") String newNameOfSurvey,
			@PathVariable(name = "survey_id") Integer surveyId) {
		return surveyService.updateNameOfSurvey(newNameOfSurvey, surveyId);
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = "{client_id}/surveys", method = RequestMethod.GET)
	public List<Survey> getAllSurveysOfClient(@PathVariable(name = "client_id") Integer clientId) {
		return surveyService.getAllSurveysOfClient(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	@RequestMapping(value = "/survey_{survey_id}/add_group", method = RequestMethod.POST)
	public Status addGroupsToSurvey(@RequestBody JSONArray json) {
		return surveyService.addGroupsToSurvey(json);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = "/groups", method = RequestMethod.GET)
	public List<Group> getGroups(@PathVariable(value = "client_id") Integer clientId) {
		return surveyService.getGroups(clientId);
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	@RequestMapping(value = "/survey_{survey_id}/groups", method = RequestMethod.GET)
	public List<Group> getGroupsSurvey(@PathVariable(value = "survey_id") Integer surveyId) {
		return surveyService.getGroupsSurvey(surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	@RequestMapping(value = "/survey_{survey_id}", method = RequestMethod.DELETE)
	public Status deleteSurvey(@PathVariable(value = "survey_id") Integer surveyId) {
		return surveyService.deleteSurvey(surveyId);
	}

}
