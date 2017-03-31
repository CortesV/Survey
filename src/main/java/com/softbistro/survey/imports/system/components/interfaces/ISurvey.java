package com.softbistro.survey.imports.system.components.interfaces;

import com.softbistro.survey.imports.system.components.entities.Survey;
import com.softbistro.survey.response.Response;

public interface ISurvey {

	/**
	 * Save survey to db.
	 * 
	 * @param survey
	 * @return Integer status of saving
	 */
	public Response saveSurvey(Survey survey);
	
}
