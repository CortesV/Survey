package com.softbistro.survey.statistic.component.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softbistro.survey.statistic.component.entity.ParticipantAttributes;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticExport;
import com.softbistro.survey.statistic.component.interfacee.IExportStatisticDao;
import com.softbistro.survey.statistic.export.csv.CsvStatisticService;

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
			List<SurveyStatisticExport> surveyStatisticExport = generalStatisticDao.getAllStatistic();
			return gson.toJson(surveyStatisticExport);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}