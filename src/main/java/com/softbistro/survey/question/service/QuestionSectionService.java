package com.softbistro.survey.question.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> setQuestionSection(QuestionSection questionSection) {
		return iQuestionSection.setQuestionSection(questionSection);
	}

	/**
	 * Method for updating QuestionSection
	 * 
	 * @param questionSection
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> updateQuestionSection(QuestionSection questionSection, Integer questionSectionId) {
		return iQuestionSection.updateQuestionSection(questionSection, questionSectionId);
	}

	/**
	 * Method for deleting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	public ResponseEntity<Object> deleteQuestionSection(Integer questionSectionId) {
		return iQuestionSection.deleteQuestionSection(questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by id
	 * 
	 * @param questionSectionId
	 * @return ResponseEntity
	 */
	public ResponseEntity<QuestionSection> getQuestionSectionById(Integer questionSectionId) {
		return iQuestionSection.getQuestionSectionById(questionSectionId);
	}

	/**
	 * Method to getting QuestionSection from db by surveyId
	 * 
	 * @param surveyId
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<QuestionSection>> getQuestionSectionBySurveyId(Integer surveyId) {
		return iQuestionSection.getQuestionSectionBySurveyId(surveyId);
	}
}
