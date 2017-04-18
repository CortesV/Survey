package com.softbistro.survey.statistic.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.softbistro.survey.statistic.component.interfacee.ExportFile;
import com.softbistro.survey.statistic.component.service.XmlStatisticDao;
import com.softbistro.survey.statistic.export.ExportStatisticService;

/**
 * Export data to file
 * @author alex_alokhin
 *
 */
@Service
public class XMLServiceStatistic implements ExportFile{

	@Autowired
	private ExportStatisticService xmlService;
	
	@Autowired
	private XmlStatisticDao xmlDao;
	
	/**
	 * Export statistic about surveys to xml file
	 * @param path - path to file
	 * @return - file with content
	 */
	public File export(String path) {
		return xmlService.storeDataToFile(xmlDao.export(),path);
	}
	
}
