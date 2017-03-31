package com.softbistro.survey.question.controller;

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

			return new ResponseEntity<Question>(HttpStatus.UNAUTHORIZED);
		}

		return questionService.findQuestionById(id);
	}

	/**
	 * Save client to database
	 * 
	 * @param question
	 *            question - all information about question that will write to
	 *            database
	 * @return return - status of execution this method
	 */
	@ApiOperation(value = "Create new Question", notes = "Create question instanse by survey_id, question, description_short,"
			+ " description_long, question_section_id, answer_type, question_choices, required, required comment", tags = "Question")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Object> saveQuestion(@RequestBody Question question, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return questionService.saveQuestion(question);
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

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return questionService.deleteQuestion(id);
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
	@ApiOperation(value = "Update Question By Id", notes = "Update question instanse by survey_id, question, description_short,"
			+ " description_long, question_section_id, answer_type, question_choices, required and question id", tags = "Question")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateQuestion(@RequestBody Question question, @PathVariable("id") Long id,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}

		return questionService.updateQuestion(question, id);
	}
}
