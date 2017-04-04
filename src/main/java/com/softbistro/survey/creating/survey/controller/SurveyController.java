package com.softbistro.survey.creating.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/rest/survey/v1/survey")
public class SurveyController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private SurveyService surveyService;

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Create new Survey", notes = "Create new survey instanse by client id, name, theme, start time, finish time", tags = "Survey")
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Integer> create(@RequestBody Survey survey, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Integer>(HttpStatus.UNAUTHORIZED);
		}

		return surveyService.createSurvey(survey);
	}

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	@ApiOperation(value = "Update Survey", notes = "Update survey instanse by client id, name, theme, start time, finish time and survey id", tags = "Survey")
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Object> update(@RequestBody Survey survey, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return surveyService.updateOfSurvey(survey);
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	@ApiOperation(value = "Get all Surveys of Client", notes = "Get all Surveys of Client by client id", tags = "Survey")
	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Survey>> getAllSurveysByClient(@PathVariable(name = "client_id") Integer clientId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Survey>>(HttpStatus.UNAUTHORIZED);
		}

		return surveyService.getAllSurveysOfClient(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	@ApiOperation(value = "Add Groups to Survey", notes = "Add groups to survey by list of groups", tags = "Survey")
	@RequestMapping(value = "/groups", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addGroupsToSurvey(@RequestBody List<Group> groups, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return surveyService.addGroupsToSurvey(groups);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	@ApiOperation(value = "Get all Groups of Client", notes = "Get all groups of Client by client id", tags = "Survey")
	@RequestMapping(value = "/client/{client_id}/groups", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Group>> getGroups(@PathVariable(value = "client_id") Integer clientId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Group>>(HttpStatus.UNAUTHORIZED);
		}

		return surveyService.getGroups(clientId);
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	@ApiOperation(value = "Get all Groups of Survey", notes = "Get all Groups of Survey by survey id", tags = "Survey")
	@RequestMapping(value = "/{survey_id}/groups", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Group>> getGroupsSurvey(@PathVariable(value = "survey_id") Integer surveyId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<List<Group>>(HttpStatus.UNAUTHORIZED);
		}

		return surveyService.getGroupsSurvey(surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	@ApiOperation(value = "Delete Survey By Id", notes = "Delete survey from data base by survey id", tags = "Survey")
	@RequestMapping(value = "/{survey_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> deleteSurvey(@PathVariable(value = "survey_id") Integer surveyId,
			@RequestHeader String token) {
		//
		// if (!authorizationService.checkAccess(token)) {
		//
		// return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		// }

		return surveyService.deleteSurvey(surveyId);
	}

	/**
	 * Start survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/{survey_id}/start", method = RequestMethod.PUT)
	public ResponseEntity<Object> startSurvey(@PathVariable(value = "survey_id") Integer surveyId) {
		return surveyService.startSurvey(surveyId);
	}

	/**
	 * Stop survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(value = "/{survey_id}/stop", method = RequestMethod.PUT)
	public ResponseEntity<Object> stopSurvey(@PathVariable(value = "survey_id") Integer surveyId) {
		return surveyService.stopSurvey(surveyId);
	}
}
