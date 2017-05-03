package com.softbistro.survey.imports.system.components.interfaces;

import com.softbistro.survey.imports.system.components.entities.ImportSurvey;

/**
 * Save survey into db
 * 
 * @author olegnovatskiy
 */
public interface IImportSurveyDAO {

	/**
	 * Save survey to db.
	 * 
	 * @param survey
	 * @return Integer status of saving
	 */
	public Integer saveSurvey(ImportSurvey savedSurvey);

}
