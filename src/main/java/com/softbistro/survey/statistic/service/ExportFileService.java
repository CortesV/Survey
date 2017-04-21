package com.softbistro.survey.statistic.service;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.interfacee.IExportFile;

/**
 * Export data to file with specified extension
 * @author alex_alokhin
 *
 */
@Service
public class ExportFileService {
	@Autowired
	private GenericApplicationContext applicationContext;
	
	public File exportToFile(String extension){
		IExportFile exportSurvey;
		extension = extension.toLowerCase();
		switch (extension) {
		case "xml":
			exportSurvey = (IExportFile) applicationContext.getBean("xmlStatisticService");	
			break;
		case "csv":
			exportSurvey = (IExportFile) applicationContext.getBean("csvStatisticService");	
			break;
		case "json":
			exportSurvey = (IExportFile) applicationContext.getBean("jsonStatisticService");
			break;
		case "xlsx":
			exportSurvey = (IExportFile) applicationContext.getBean("xlsxStatisticService");	
			break;
		default:	
			exportSurvey = null;	
			break;
		}
		return exportSurvey.exportToFile(extension);
	}
}
