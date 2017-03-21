package com.softbistro.survey.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.question.service.QuestionSectionService;
import com.softbistro.survey.response.Response;

/**
 * Controller for QuestionSectionController
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/questionSection")
public class QuestionSectionController {

	@Autowired
	QuestionSectionService questionSectionService;

	/**
	 * Method for creating QuestionSection
	 * 
	 * @param questionSection
	 * @return Response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Response setQuestionSection(@RequestBody QuestionSection questionSection) {
		return questionSectionService.setQuestionSection(questionSection);
	}

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Response updateQuestionSection(@RequestBody QuestionSection questionSection,
			@PathVariable("id") Integer questionSectionId) {
		return questionSectionService.updateQuestionSection(questionSection, questionSectionId);
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteQuestionSection(@PathVariable("id") Integer questionSectionId) {
		return questionSectionService.deleteQuestionSection(questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response getQuestionSectionById(@PathVariable Integer id) {
		return questionSectionService.getQuestionSectionById(id);
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return Response
	 */
	@RequestMapping(value = "/survey/{id}", method = RequestMethod.GET)
	public Response getQuestionSectionBySurveyId(@PathVariable Integer id) {
		return questionSectionService.getQuestionSectionBySurveyId(id);
	}

	/**
	 * Method to getting QuestionSection from db by section name
	 * 
	 * @param name
	 * @return Response
	 */
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public Response getQuestionSectionById(@PathVariable String name) {
		return questionSectionService.getQuestionSectionByName(name);
	}
}
