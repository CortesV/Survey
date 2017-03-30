package com.softbistro.survey.question.components.interfaces;

import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.response.Response;

/**
 * Interface for question section entity
 * 
 * @author af150416
 *
 */
public interface IQuestionSection {

	/**
	 * Method for creating QuestionSection
	 * 
	 * @param questionSection
	 * @return Response
	 */
	public Response setQuestionSection(QuestionSection questionSection);

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection
	 * @return Response
	 */
	public Response updateQuestionSection(QuestionSection questionSection, Integer questionSectionId);

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	public Response deleteQuestionSection(Integer questionSectionId);

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return Response
	 */
	public Response getQuestionSectionById(Integer questionSectionId);

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return Response
	 */
	public Response getQuestionSectionBySurveyId(Integer surveyId);

	/**
	 * Method to getting QuestionSection from db by section name
	 * 
	 * @param name
	 * @return Response
	 */
	public Response getQuestionSectionByName(String name);
}
