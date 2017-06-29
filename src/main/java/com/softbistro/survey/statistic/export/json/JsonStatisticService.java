package com.softbistro.survey.statistic.export.json;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.interfacee.IExportFile;
import com.softbistro.survey.statistic.component.service.JsonStatisticDao;
import com.softbistro.survey.statistic.export.StoreDataToFile;

/**
 * Export data to JSON file
 * 
 * @author alex_alokhin
 *
 */
@Service
public class JsonStatisticService implements IExportFile {

	@Autowired
	private StoreDataToFile output;

	@Autowired
	private JsonStatisticDao jsonDao;

	private final static String FILE_PATH = "src/main/resources/importing_files/statistic.";

	/**
	 * Export statistic about surveys to JSON file
	 * 
	 * @param extension
	 *            - extension of file
	 * @return - file with content
	 */
	@Override
	public File exportToFile(String extension) {
		return output.storeDataToFile(jsonDao.export(), FILE_PATH + extension);
	}
}
