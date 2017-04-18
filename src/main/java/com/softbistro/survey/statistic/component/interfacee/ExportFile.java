package com.softbistro.survey.statistic.component.interfacee;

import java.io.File;

/**
 * Method for exporting data to file
 * 
 * @author alex_alokhin
 *
 */
public interface ExportFile {

	/**
	 * Export data to file 
	 * @param path - path to file
	 * @return - return files with data
	 */
	public File export(String path);
}
