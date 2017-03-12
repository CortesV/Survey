package com.survey.softbistro.creating.survey.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.softbistro.creating.survey.component.entity.Group;
import com.survey.softbistro.creating.survey.component.entity.Survey;
import com.survey.softbistro.creating.survey.component.interfacee.ISurvey;
import com.survey.softbistro.response.Response;
import com.survey.softbistro.response.Status;

import net.minidev.json.JSONArray;

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
	public Response updateNameOfSurvey(String newNameOfSurvey, Integer surveyId) {
		return iSurvey.updateNameOfSurvey(newNameOfSurvey, surveyId);
	}

	/**
	 * Get all groups that has client
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Group> getGroups(Integer clientId) {
		return iSurvey.getGroups(clientId);
	}

	/**
	 * Adding groups that will be in survey
	 * 
	 * @param json
	 * @return
	 */
	public Status addGroupsToSurvey(JSONArray json) {
		ObjectMapper mapper = new ObjectMapper();
		try {

			List<Group> groups = mapper.readValue(json.toString(), new TypeReference<List<Group>>() {
			});

			return iSurvey.addGroupsToSurvey(groups);

		} catch (IOException e) {
			e.printStackTrace();
			return Status.ERROR;
		}

	}

}
