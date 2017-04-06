package com.softbistro.survey.creating.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.creating.survey.component.interfacee.ISurveyDao;

@Service
public class SurveyService {

	@Autowired
	private ISurveyDao iSurveyDao;

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return ResponseEntity
	 */
	public Integer create(Survey survey) {
		return iSurveyDao.create(survey);
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Survey> getAllSurveysByClient(Integer clientId) {
		return iSurveyDao.getAllSurveysByClient(clientId);
	}

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	public void update(Survey survey) {
		iSurveyDao.update(survey);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Group> getGroupsClient(Integer clientId) {
		return iSurveyDao.getGroupsClient(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	public void addGroups(List<Group> groups) {
		iSurveyDao.addGroups(groups);
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	public List<Group> getGroupsSurvey(Integer surveyId) {
		return iSurveyDao.getGroupsSurvey(surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	public void delete(Integer surveyId) {
		iSurveyDao.delete(surveyId);
	}

	/**
	 * Start working time URL for vote and functions of survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public void start(Integer surveyId) {
		iSurveyDao.start(surveyId);
	}

	/**
	 * Stop working time URL for vote and functions of survey
	 * 
	 * @param surveyId
	 * @return
	 */
	public void stop(Integer surveyId) {
		iSurveyDao.stop(surveyId);
	}

}
