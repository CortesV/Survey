package com.softbistro.survey.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.question.components.entity.Question;
import com.softbistro.survey.question.service.QuestionService;
import com.softbistro.survey.response.Response;

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

	/**
	 * Find question in database by id of question
	 * 
	 * @param id
	 *            id - id of question
	 * @return return - all information about question
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response findQuestionById(@PathVariable("id") Long id) {

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
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Response saveQuestion(@RequestBody Question question) {

		return questionService.saveQuestion(question);
	}

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteQuestion(@PathVariable("id") Long id) {

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
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Response updateQuestion(@RequestBody Question question, @PathVariable("id") Long id) {

		return questionService.updateQuestion(question, id);
	}

}
