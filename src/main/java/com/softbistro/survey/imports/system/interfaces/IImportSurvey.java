package com.softbistro.survey.imports.system.interfaces;

import com.softbistro.survey.response.Response;

public interface IImportSurvey {

	/**
	 * Import survey from file of CSV format.
	 * 
	 * @param importFileName
	 * @return
	 */
	public Response importSyrveyCSV(String importFileName, String titleSurvey, Long clientId);
}
