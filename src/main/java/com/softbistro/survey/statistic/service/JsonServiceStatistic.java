package com.softbistro.survey.statistic.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.service.JsonStatisticDao;
import com.softbistro.survey.statistic.export.ExportStatisticService;

@Service
public class JsonServiceStatistic {
	
	@Autowired
	private ExportStatisticService jsonService;
	
	@Autowired
	private JsonStatisticDao jsonDao;
	
	/**
	 * Export statistic about surveys to json file
	 * @return - file with content
	 */
	public File export() {
		return jsonService.storeDataToFile(jsonDao.export(),"src/main/resources/importing_files/statistic.json");
	}
}
