package com.softbistro.survey.statistic.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.service.XmlStatisticDao;
import com.softbistro.survey.statistic.export.ExportStatisticService;

@Service
public class XMLServiceStatistic {

	@Autowired
	private ExportStatisticService xmlService;
	
	@Autowired
	private XmlStatisticDao xmlDao;
	
	/**
	 * Export statistic about surveys
	 * @return - file with content
	 */
	public File export() {
		return xmlService.storeDataToFile(xmlDao.export(),"src/main/resources/importing_files/statistic.xml");
	}
}
