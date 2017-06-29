package com.softbistro.survey.question.components.interfaces;

import java.util.List;

import com.softbistro.survey.question.components.entity.QuestionSection;

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
	 * @return ResponseEntity
	 */
	public Integer setQuestionSection(QuestionSection questionSection);

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection
	 * @return ResponseEntity
	 */
	public void updateQuestionSection(QuestionSection questionSection, Integer questionSectionId);

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	public void deleteQuestionSection(Integer questionSectionId);

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	public QuestionSection getQuestionSectionById(Integer questionSectionId);

	/**
	 * Method to getting QuestionSection from db by clientId
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	public List<QuestionSection> getQuestionSectionByClientId(Integer clientId);

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return ResponseEntity
	 */
	public List<QuestionSection> getQuestionSectionBySurveyId(Integer surveyId);

	/**
	 * Method for adding QuestionSection to survey
	 * 
	 * @param questionSection
	 *            id, survey id
	 * @return ResponseEntity
	 */
	public void addQuestionSectionToSurvey(Integer questionSectionId, Integer surveyId);

	/**
	 * Method for deleting QuestionSection from survey
	 * 
	 * @param questionSectionId,
	 *            survey id
	 * @return ResponseEntity
	 */
	public void deleteQuestionSectionFromSurvey(Integer questionSectionId, Integer surveyId);
}
