package com.softbistro.survey.imports.system.interfaces;

import javax.servlet.http.Part;

/**
 * Importing survey from file
 * 
 * @author olegnovatskiy
 */
public interface IImportSurvey {

	/**
	 * Import survey from file diferent formats.
	 * 
	 * @param importFileName
	 * @return
	 */
	public void fromFile(Part filePart, Integer clientId);
}
