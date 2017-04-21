package com.softbistro.survey.statistic.service;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.interfacee.IExportFile;
import com.softbistro.survey.statistic.export.csv.CsvStatisticService;
import com.softbistro.survey.statistic.export.json.JsonStatisticService;
import com.softbistro.survey.statistic.export.xlsx.XlsxStatisticService;
import com.softbistro.survey.statistic.export.xml.XmlStatisticService;

/**
 * Export data to file with specified extension
 * @author alex_alokhin
 *
 */
@Service
public class ExportFileService {
	@Autowired
	private GenericApplicationContext applicationContext;
	@Autowired
	private XmlStatisticService xmlStatisticService;
	@Autowired
	private JsonStatisticService jsonStatisticService;
	@Autowired
	private XlsxStatisticService xlsxStatisticService;
	@Autowired
	private CsvStatisticService csvStatisticService;
	
	public File exportToFile(String extension){
		IExportFile exportSurvey;
		extension = extension.toLowerCase();
		switch (extension) {
		case "xml":
			exportSurvey = xmlStatisticService;	
			break;
		case "csv":
			exportSurvey = csvStatisticService;
			break;
		case "json":
			exportSurvey = jsonStatisticService;
			break;
		case "xlsx":
			exportSurvey = xlsxStatisticService;
			break;
		default:	
			exportSurvey = null;	
			break;
		}
		return exportSurvey.exportToFile(extension);
	}
}
