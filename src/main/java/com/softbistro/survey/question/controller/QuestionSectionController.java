package com.softbistro.survey.question.controller;

import java.util.List;

import org.apache.log4j.Logger;
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
import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.question.service.QuestionSectionService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for QuestionSectionController
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/questionSection")
public class QuestionSectionController {

	private static final Logger LOGGER = Logger.getLogger(QuestionSectionController.class);

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private QuestionSectionService questionSectionService;

	/**
	 * Method for creating QuestionSection
	 * 
	 * @param questionSection
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Create new QuestionSection", notes = "Create new question section instanse by survey id, section name, short description, long description", tags = "Question Section")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> setQuestionSection(@RequestBody QuestionSection questionSection,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(questionSectionService.setQuestionSection(questionSection), HttpStatus.CREATED);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Update QuestionSection", notes = "Update question section instanse by survey id, section name, short description, long description and question section id", tags = "Question Section")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateQuestionSection(@RequestBody QuestionSection questionSection,
			@PathVariable("id") Integer questionSectionId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			questionSectionService.updateQuestionSection(questionSection, questionSectionId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete QuestionSection", notes = "Delete question section instanse by question section id", tags = "Question Section")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteQuestionSection(@PathVariable("id") Integer questionSectionId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			questionSectionService.deleteQuestionSection(questionSectionId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get QuestionSection By Id", notes = "Get question section instanse by question section id", tags = "Question Section")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<QuestionSection> getQuestionSectionById(@PathVariable Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(questionSectionService.getQuestionSectionById(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to getting QuestionSection from db by clientId
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get QuestionSection By Client", notes = "Get question section instanse by client id", tags = "Question Section")
	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionSection>> getQuestionSectionByClientId(@PathVariable Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(questionSectionService.getQuestionSectionByClientId(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Get QuestionSection By Survey", notes = "Get question section instanse by survey id", tags = "Question Section")
	@RequestMapping(value = "/survey/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionSection>> getQuestionSectionBySurveyId(@PathVariable Integer id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(questionSectionService.getQuestionSectionBySurveyId(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for adding QuestionSection to survey
	 * 
	 * @param questionSection
	 *            id, survey id
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Adding Question Section to Survey", notes = "Add question section to survey by question section id and survey id", tags = "Question Section")
	@RequestMapping(value = "/{questionSection_id}/{survey_id}", method = RequestMethod.POST)
	public ResponseEntity<Object> addQuestionSectionToSurvey(
			@PathVariable("questionSection_id") Integer questionSectionId, @PathVariable("survey_id") Integer surveyId,
			@RequestHeader String token) {
		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			questionSectionService.addQuestionSectionToSurvey(questionSectionId, surveyId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method for deleting QuestionSection from survey
	 * 
	 * @param questionSectionId,
	 *            survey id
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Delete QuestionSection from Survey", notes = "Delete question section from survey by question section id and survey id", tags = "Question Section")
	@RequestMapping(value = "/{questionSection_id}/{survey_id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteQuestionSectionFromSurvey(
			@PathVariable("questionSection_id") Integer questionSectionId, @PathVariable("survey_id") Integer surveyId,
			@RequestHeader String token) {
		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			questionSectionService.deleteQuestionSectionFromSurvey(questionSectionId, surveyId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
