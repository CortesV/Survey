package com.softbistro.survey.statistic.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.interfacee.ExportFile;
import com.softbistro.survey.statistic.component.service.JsonStatisticDao;
import com.softbistro.survey.statistic.export.ExportStatisticService;

/**
 * Export data to file
 * @author alex_alokhin
 *
 */
@Service
public class JsonServiceStatistic implements ExportFile{
	
	@Autowired
	private ExportStatisticService jsonService;
	
	@Autowired
	private JsonStatisticDao jsonDao;
	
	/**
	 * Export statistic about surveys to json file
	 * @param path - path to file
	 * @return - file with content
	 */
	@Override
	public File export(String path) {
		return jsonService.storeDataToFile(jsonDao.export(),path);
	}
}
