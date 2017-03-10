package com.survey.softbistro.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.survey.softbistro.components.entity.Question;
import com.survey.softbistro.service.QuestionService;

/**
 * Controller for CRUD of Client
 * 
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	/**
	 * Find question in database by id of question
	 * 
	 * @param id
	 *            id - id of question
	 * @return return - all information about question
	 */
	@RequestMapping(value = "/getQuestion", method = RequestMethod.GET)
	public Question findQuestionById(@RequestHeader Long id) {

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
	@RequestMapping(value = "/saveQuestion", method = RequestMethod.POST)
	public Map<String, String> saveQuestion(@RequestBody Question question) {

		return questionService.saveQuestion(question);
	}

	/**
	 * Delete question from database by id of question
	 * 
	 * @param id-id
	 *            of question
	 * @return return - status of execution this method
	 */
	@RequestMapping(value = "/deleteQuestion", method = RequestMethod.DELETE)
	public Map<String, String> deleteQuestion(@RequestHeader Long id) {

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
	@RequestMapping(value = "/updateQuestion", method = RequestMethod.PUT)
	public Map<String, String> updateQuestion(@RequestBody Question question, @RequestHeader Long id) {

		return questionService.updateQuestion(question, id);
	}

}
