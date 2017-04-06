package com.softbistro.survey.creating.survey.component.interfacee;

import java.util.List;

import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;

public interface ISurveyDao {

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return ResponseEntity
	 */
	public Integer create(Survey survey);

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	public void update(Survey survey);

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Survey> getAllSurveysByClient(Integer clientId);

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Group> getGroupsClient(Integer clientId);

	/**
	 * Add groups of participant that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	public void addGroups(List<Group> groups);

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	public List<Group> getGroupsSurvey(Integer surveyId);

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	public void delete(Integer surveyId);

	/**
	 * Start working time URL for vote and functions of survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public void start(Integer surveyId);

	/**
	 * Stop working time URL for vote and functions of survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public void stop(Integer surveyId);

}
