package com.softbistro.survey.statistic.component.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softbistro.survey.statistic.component.interfacee.IExportStatisticDao;

/**
 * Working with database to get statistic data
 * 
 * @author alex_alokhin
 *
 */
@Repository
public class JsonStatisticDao implements IExportStatisticDao {

	@Autowired
	private GeneralStatisticDao generalStatisticDao;
	
	private static final Logger LOG = Logger.getLogger(JsonStatisticDao.class);
	
	/**
	 * Export statistic about surveys to string in JSON format 
	 * @return - string with data in JSON format
	 */
	@Override
	public String export() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(generalStatisticDao.getAllStatistic());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}