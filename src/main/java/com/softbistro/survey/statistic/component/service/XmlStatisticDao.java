package com.softbistro.survey.statistic.component.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.statistic.component.interfacee.IExportStatisticDao;
import com.thoughtworks.xstream.XStream;

/**
 * Working with database to get statistic data
 * 
 * @author alex_alokhin
 *
 */
@Repository
public class XmlStatisticDao implements IExportStatisticDao{
	
	@Autowired
	private GeneralStatisticDao generalStatisticDao;
	
	private static final Logger LOG = Logger.getLogger(XmlStatisticDao.class);
	
	/**
	 * Export statistic about surveys to string in XML format 
	 * @return - string with data in XML format
	 */
	@Override
	public String export() {
		try {
			XStream xstream = new XStream();
			return xstream.toXML(generalStatisticDao.getAllStatistic());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
		
	}
	
	
	
}
