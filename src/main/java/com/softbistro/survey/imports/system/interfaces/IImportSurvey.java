package com.softbistro.survey.imports.system.interfaces;

import javax.servlet.http.HttpServletRequest;

/**
 * Importing survey from file
 * 
 * @author olegnovatskiy
 */
public interface IImportSurvey {

	/**
	 * Import survey from file of CSV format.
	 * 
	 * @param importFileName
	 * @return
	 */
	public void importSyrveyCSV(HttpServletRequest request, Long clientId);
}
