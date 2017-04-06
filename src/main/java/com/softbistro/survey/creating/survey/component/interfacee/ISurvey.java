package com.softbistro.survey.creating.survey.component.interfacee;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;

public interface ISurvey {

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return ResponseEntity
	 */
	public ResponseEntity<Integer> createSurvey(Survey survey);

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	public ResponseEntity<Object> updateOfSurvey(Survey survey);

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	public ResponseEntity<List<Survey>> getAllSurveysOfClient(Integer clientId);

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	public ResponseEntity<List<Group>> getGroupsClient(Integer clientId);

	/**
	 * Add groups of participant that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	public ResponseEntity<Object> addGroupsToSurvey(List<Group> groups);

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	public ResponseEntity<List<Group>> getGroupsSurvey(Integer surveyId);

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	public ResponseEntity<Object> deleteSurvey(Integer surveyId);

	/**
	 * Start survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public ResponseEntity<Object> startSurvey(Integer surveyId);

	/**
	 * Start survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public ResponseEntity<Object> stopSurvey(Integer surveyId);

}
