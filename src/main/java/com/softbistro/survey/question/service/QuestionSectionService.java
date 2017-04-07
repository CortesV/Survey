package com.softbistro.survey.question.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.question.components.entity.QuestionSection;
import com.softbistro.survey.question.components.interfaces.IQuestionSection;

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
	 * @return ResponseEntity
	 */
	public void setQuestionSection(QuestionSection questionSection) {

		iQuestionSection.setQuestionSection(questionSection);
	}

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection
	 * @return ResponseEntity
	 */
	public void updateQuestionSection(QuestionSection questionSection, Integer questionSectionId) {

		iQuestionSection.updateQuestionSection(questionSection, questionSectionId);
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	public void deleteQuestionSection(Integer questionSectionId) {

		iQuestionSection.deleteQuestionSection(questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	public QuestionSection getQuestionSectionById(Integer questionSectionId) {

		return iQuestionSection.getQuestionSectionById(questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by clientId
	 * 
	 * @param clientId
	 * @return ResponseEntity
	 */
	public List<QuestionSection> getQuestionSectionByClientId(Integer clientId) {

		return iQuestionSection.getQuestionSectionByClientId(clientId);
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return ResponseEntity
	 */
	public List<QuestionSection> getQuestionSectionBySurveyId(Integer surveyId) {

		return iQuestionSection.getQuestionSectionBySurveyId(surveyId);
	}

	/**
	 * Method for adding QuestionSection to survey
	 * 
	 * @param questionSection
	 *            id, survey id
	 * @return ResponseEntity
	 */
	public void addQuestionSectionToSurvey(Integer questionSectionId, Integer surveyId) {

		iQuestionSection.addQuestionSectionToSurvey(questionSectionId, surveyId);
	}

	/**
	 * Method for deleting QuestionSection from survey
	 * 
	 * @param questionSectionId,
	 *            survey id
	 * @return ResponseEntity
	 */
	public void deleteQuestionSectionFromSurvey(Integer questionSectionId, Integer surveyId) {

		iQuestionSection.deleteQuestionSectionFromSurvey(questionSectionId, surveyId);
	}
}
