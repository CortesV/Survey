package com.softbistro.survey.question.controller;

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
import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.question.service.QuestionService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for CRUD of Client
 * 
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/rest/survey/v1/question")
public class QuestionController {

	private static final Logger LOGGER = Logger.getLogger(QuestionController.class);

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AuthorizationService authorizationService;

	/**
	 * Find question in database by id of question
	 * 
	 * @param id
	 *            id - id of question
	 * @return return - all information about question
	 */
	@ApiOperation(value = "Get Question By Id", notes = "Get question instanse by question id", tags = "Question")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Question> findQuestionById(@PathVariable("id") Long id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			return new ResponseEntity<>(questionService.findQuestionById(id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Save client to database
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	@ApiOperation(value = "Create new Question", notes = "Create question instanse by survey id, question, description short,"
			+ " description Long, questionSection Id, answer Type, question Choices, required, required Comment. "
			+ "Field answerType can have value such as: RATE1-10, RATE1-5, RATE1-3, BOOLEAN, LIST, MULTILIST, INPUT, MEMO", tags = "Question")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Object> saveQuestion(@RequestBody Question question, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			questionService.saveQuestion(question);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	@ApiOperation(value = "Delete Question By Id", notes = "Delete question instanse by question id", tags = "Question")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteQuestion(@PathVariable("id") Long id, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			questionService.deleteQuestion(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update information of question
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @param id
	 *            id-id of question
	 * @return return - status of execution this method
	 */
	@ApiOperation(value = "Update Question By Id", notes = "Update question instanse by survey id, question, description short,"
			+ " description Long, questionSection Id, answer Type, question Choices, required, required Comment and question id. "
			+ "Field answerType can have value such as: RATE1-10, RATE1-5, RATE1-3, BOOLEAN, LIST, MULTILIST, INPUT, MEMO", tags = "Question")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateQuestion(@RequestBody Question question, @PathVariable("id") Long id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {

			questionService.updateQuestion(question, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
