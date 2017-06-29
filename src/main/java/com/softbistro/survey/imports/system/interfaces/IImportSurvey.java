package com.softbistro.survey.imports.system.interfaces;

import javax.servlet.http.Part;

/**
 * Importing survey from file
 * 
 * @author olegnovatskiy
 */
public interface IImportSurvey {

	/**
	 * Import survey from file different formats.
	 * 
	 * @param importFileName
	 * @return
	 */
	public Integer fromFile(Part filePart, Integer clientId);
}
