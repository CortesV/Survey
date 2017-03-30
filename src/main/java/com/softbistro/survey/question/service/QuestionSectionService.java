package com.softbistro.survey.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.question.components.interfaces.IQuestionSection;
import com.softbistro.survey.response.Response;

/**
 * Service for QuestionSection entity
 * 
 * @author af150416
 *
 */
@Service
public class QuestionSectionService {

	@Autowired
	private IQuestionSection iQuestionSection;

	/**
	 * Method for creating QuestionSection
	 * 
	 * @param questionSection
	 * @return Response
	 */
	public Response setQuestionSection(QuestionSection questionSection) {
		return iQuestionSection.setQuestionSection(questionSection);
	}

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection
	 * @return Response
	 */
	public Response updateQuestionSection(QuestionSection questionSection, Integer questionSectionId) {
		return iQuestionSection.updateQuestionSection(questionSection, questionSectionId);
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	public Response deleteQuestionSection(Integer questionSectionId) {
		return iQuestionSection.deleteQuestionSection(questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	public Response getQuestionSectionById(Integer questionSectionId) {
		return iQuestionSection.getQuestionSectionById(questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return Response
	 */
	public Response getQuestionSectionBySurveyId(Integer surveyId) {
		return iQuestionSection.getQuestionSectionBySurveyId(surveyId);
	}

	/**
	 * Method to getting QuestionSection from db by section name
	 * 
	 * @param name
	 * @return Response
	 */
	public Response getQuestionSectionByName(String name) {
		return iQuestionSection.getQuestionSectionByName(name);
	}
}
