package com.softbistro.survey.statistic.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.interfacee.IExportFile;
import com.softbistro.survey.statistic.export.csv.CsvStatisticService;
import com.softbistro.survey.statistic.export.json.JsonStatisticService;
import com.softbistro.survey.statistic.export.xlsx.XlsxStatisticService;
import com.softbistro.survey.statistic.export.xml.XmlStatisticService;

/**
 * Export data to file with specified extension
 * 
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

	private static final String CSV = "csv";
	private static final String XML = "xml";
	private static final String XLSX = "xlsx";
	private static final String JSON = "json";

	public File exportToFile(String extension) {
		IExportFile exportSurvey;
		switch (extension.toLowerCase()) {
		case XML:
			exportSurvey = xmlStatisticService;
			break;
		case CSV:
			exportSurvey = csvStatisticService;
			break;
		case JSON:
			exportSurvey = jsonStatisticService;
			break;
		case XLSX:
			exportSurvey = xlsxStatisticService;
			break;
		default:
			exportSurvey = null;
			break;
		}
		return exportSurvey.exportToFile(extension);
	}
}
