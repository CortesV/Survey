package com.survey.softbistro.creating.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.softbistro.creating.survey.component.entity.Group;
import com.survey.softbistro.creating.survey.component.entity.Survey;
import com.survey.softbistro.creating.survey.component.interfacee.ISurvey;
import com.survey.softbistro.response.Response;
import com.survey.softbistro.response.Status;

@Service
public class SurveyService {

	@Autowired
	ISurvey iSurvey;

	/**
	 * Writing new survey into database.
	 * 
	 * @param survey
	 *            - parsed JSON with information about survey.
	 * @return Response
	 */
	public Response createSurvey(Survey survey) {
		return iSurvey.createSurvey(survey);
	}

	/**
	 * Get all surveys from one client
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Survey> getAllSurveysOfClient(Integer clientId) {
		return iSurvey.getAllSurveysOfClient(clientId);
	}

	/**
	 * Update name of survey in database
	 * 
	 * @param newNameOfSurve
	 * @param surveyId
	 *            - id of survey that will be changed
	 */
	public Response updateOfSurvey(Survey survey) {
		return iSurvey.updateOfSurvey(survey);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Group> getGroups(Integer clientId) {
		return iSurvey.getGroupsClient(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	public Status addGroupsToSurvey(List<Group> groups) {
		return iSurvey.addGroupsToSurvey(groups);
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	public List<Group> getGroupsSurvey(Integer surveyId) {
		return iSurvey.getGroupsSurvey(surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	public Status deleteSurvey(Integer surveyId) {
		return iSurvey.deleteSurvey(surveyId);
	}

}
