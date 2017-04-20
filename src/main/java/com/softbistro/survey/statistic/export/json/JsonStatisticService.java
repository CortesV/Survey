package com.softbistro.survey.statistic.export.json;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.interfacee.IExportFile;
import com.softbistro.survey.statistic.component.service.JsonStatisticDao;
import com.softbistro.survey.statistic.export.StoreDataToFile;

/**
 * Export data to JSON file
 * @author alex_alokhin
 *
 */
@Service
public class JsonStatisticService implements IExportFile{
	
	@Autowired
	private StoreDataToFile output;
	
	@Autowired
	private JsonStatisticDao jsonDao;
	
	/**
	 * Export statistic about surveys to JSON file
	 * @param extension - extension of file
	 * @return - file with content
	 */
	@Override
	public File exportToFile(String extension) {
		return output.storeDataToFile(jsonDao.export(),"src/main/resources/importing_files/statistic."+extension);
	}
}
