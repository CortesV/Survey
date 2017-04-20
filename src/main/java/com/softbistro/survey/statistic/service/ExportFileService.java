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
		switch (extension) {
		case "xml":
		case "XML":
			exportSurvey = (IExportFile) applicationContext.getBean("xmlStatisticService");	
			break;
		case "csv":
		case "CSV":
			exportSurvey = (IExportFile) applicationContext.getBean("csvStatisticService");	
			break;
		case "json":
		case "JSON":
			exportSurvey = (IExportFile) applicationContext.getBean("jsonStatisticService");
			break;
		case "xlsx":
		case "XLSX":
			exportSurvey = (IExportFile) applicationContext.getBean("xlsxStatisticService");	
			break;
		default:	
			exportSurvey = null;	
			break;
		}
		return exportSurvey.exportToFile(extension);
	}
}
