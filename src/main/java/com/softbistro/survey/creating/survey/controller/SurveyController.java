package com.softbistro.survey.creating.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.creating.survey.service.SurveyService;
import com.softbistro.survey.response.Response;

@RestController
@RequestMapping(value = "/rest/survey/v1/survey")
public class SurveyController {

	private static final String UNAUTHORIZED_CLIENT = "Unauthorized client";

	@Autowired
	private AuthorizationService authorizationService;

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
	public Response create(@RequestBody Survey survey, @RequestHeader String token) {

		// if (!authorizationService.checkAccess(token)) {

		// return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		// }

		return surveyService.createSurvey(survey);
	}

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Response update(@RequestBody Survey survey, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return surveyService.updateOfSurvey(survey);
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.GET)
	public Response getAllSurveysByClient(@PathVariable(name = "client_id") Integer clientId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return surveyService.getAllSurveysOfClient(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	@RequestMapping(value = "/groups", method = RequestMethod.POST)
	public Response addGroupsToSurvey(@RequestBody List<Group> groups, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return surveyService.addGroupsToSurvey(groups);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = "/client/{client_id}/groups", method = RequestMethod.GET)
	public Response getGroups(@PathVariable(value = "client_id") Integer clientId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return surveyService.getGroups(clientId);
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	@RequestMapping(value = "/{survey_id}/groups", method = RequestMethod.GET)
	public Response getGroupsSurvey(@PathVariable(value = "survey_id") Integer surveyId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return surveyService.getGroupsSurvey(surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	@RequestMapping(value = "/{survey_id}", method = RequestMethod.DELETE)
	public Response deleteSurvey(@PathVariable(value = "survey_id") Integer surveyId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new Response(null, HttpStatus.OK, UNAUTHORIZED_CLIENT);
		}

		return surveyService.deleteSurvey(surveyId);
	}

}
