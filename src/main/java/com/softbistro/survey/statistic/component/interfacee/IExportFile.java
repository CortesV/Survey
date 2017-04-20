package com.softbistro.survey.statistic.component.interfacee;

import java.io.File;

public interface IExportFile {

	/**
	 * Export data to file 
	 * @param extension - extension of file
	 * @return - return file with data
	 */
	public File exportToFile(String extension);
}
