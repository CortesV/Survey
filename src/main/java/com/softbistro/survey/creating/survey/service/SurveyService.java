package com.softbistro.survey.creating.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.creating.survey.component.interfacee.ISurvey;

@Service
public class SurveyService {

	@Autowired
	private ISurvey iSurvey;

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return ResponseEntity
	 */
	public ResponseEntity<Integer> createSurvey(Survey survey) {
		return iSurvey.createSurvey(survey);
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	public ResponseEntity<List<Survey>> getAllSurveysOfClient(Integer clientId) {
		return iSurvey.getAllSurveysOfClient(clientId);
	}

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	public ResponseEntity<Object> updateOfSurvey(Survey survey) {
		return iSurvey.updateOfSurvey(survey);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	public ResponseEntity<List<Group>> getGroups(Integer clientId) {
		return iSurvey.getGroupsClient(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	public ResponseEntity<Object> addGroupsToSurvey(List<Group> groups) {
		return iSurvey.addGroupsToSurvey(groups);
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	public ResponseEntity<List<Group>> getGroupsSurvey(Integer surveyId) {
		return iSurvey.getGroupsSurvey(surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	public ResponseEntity<Object> deleteSurvey(Integer surveyId) {
		return iSurvey.deleteSurvey(surveyId);
	}

	/**
	 * Start survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public ResponseEntity<Object> startSurvey(Integer surveyId) {
		return iSurvey.startSurvey(surveyId);
	}

	/**
	 * Stop survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public ResponseEntity<Object> stopSurvey(Integer surveyId) {
		return iSurvey.stopSurvey(surveyId);
	}

}
