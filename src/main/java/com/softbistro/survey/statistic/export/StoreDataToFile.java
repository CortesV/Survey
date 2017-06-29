package com.softbistro.survey.statistic.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.service.StatisticService;

/**
 * Store data to file
 * 
 * @author alex_alokhin
 *
 */
@Service
public class StoreDataToFile {

	private static final Logger LOGGER = Logger.getLogger(StatisticService.class);

	/**
	 * 
	 * @param content
	 *            - content that stores to the file path - path to the file
	 * @return - file with content
	 */
	public File storeDataToFile(String content, String path) {
		FileOutputStream fop = null;
		File file = null;

		try {
			file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}

			fop = new FileOutputStream(file);

			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return null;
		} finally {
			file.deleteOnExit();
		}
		return file;
	}

}
