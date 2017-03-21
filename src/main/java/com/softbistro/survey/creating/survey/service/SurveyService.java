package com.softbistro.survey.creating.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.creating.survey.component.entity.Group;
import com.softbistro.survey.creating.survey.component.entity.Survey;
import com.softbistro.survey.creating.survey.component.interfacee.ISurvey;
import com.softbistro.survey.response.Response;

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
	public Response getAllSurveysOfClient(Integer clientId) {
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
	public Response getGroups(Integer clientId) {
		return iSurvey.getGroupsClient(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param groups
	 * @return
	 */
	public Response addGroupsToSurvey(List<Group> groups) {
		return iSurvey.addGroupsToSurvey(groups);
	}

	/**
	 * Get all groups that has survey
	 * 
	 * @param surveyId
	 */
	public Response getGroupsSurvey(Integer surveyId) {
		return iSurvey.getGroupsSurvey(surveyId);
	}

	/**
	 * 
	 * Delete survey from database
	 * 
	 * @param surveyId
	 */
	public Response deleteSurvey(Integer surveyId) {
		return iSurvey.deleteSurvey(surveyId);
	}

}
