package com.softbistro.survey.imports.system.components.interfaces;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.imports.system.components.entities.Survey;

/**
 * Save survey into db
 *  
 * @author olegnovatskiy
 */
public interface ISurveyDAO {

	/**
	 * Save survey to db.
	 * 
	 * @param survey
	 * @return Integer status of saving
	 */
	public ResponseEntity<Object> saveSurvey(Survey savedSurvey);
	
}
