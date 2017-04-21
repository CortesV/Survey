package com.softbistro.survey.statistic.export.xml;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.statistic.component.interfacee.IExportFile;
import com.softbistro.survey.statistic.component.service.XmlStatisticDao;
import com.softbistro.survey.statistic.export.StoreDataToFile;
import com.softbistro.survey.statistic.service.StatisticService;

/**
 * Export data to XML file
 * @author alex_alokhin
 *
 */
@Service
public class XmlStatisticService implements IExportFile{

	@Autowired
	private StoreDataToFile output;
	
	@Autowired
	private XmlStatisticDao xmlDao;
	
	private final static String FILE_PATH = "src/main/resources/importing_files/statistic.";
	
	private static final Logger LOG = Logger.getLogger(XmlStatisticService.class);
	
	/**
	 * Export statistic about surveys to XML file
	 * @param extension - extension of file
	 * @return - file with content
	 */
	@Override
	public File exportToFile(String extension) {
		try {
			return output.storeDataToFile(xmlDao.export(),FILE_PATH+extension);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
