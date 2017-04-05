package com.softbistro.survey.imports.system.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

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
	public ResponseEntity<Object> importSyrveyCSV(HttpServletRequest request, Long clientId);
}
