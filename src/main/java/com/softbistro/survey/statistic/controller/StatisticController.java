package com.softbistro.survey.statistic.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
import com.softbistro.survey.statistic.service.CsvServiceStatistic;
import com.softbistro.survey.statistic.service.JsonServiceStatistic;
import com.softbistro.survey.statistic.service.StatisticService;
import com.softbistro.survey.statistic.service.XMLServiceStatistic;
import com.softbistro.survey.statistic.service.XlsxServiceStatistic;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("rest/survey/v1/statistic/")
public class StatisticController {

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private JsonServiceStatistic jsonServiceStatistic;
	
	@Autowired
	private XMLServiceStatistic xmlServiceStatistic;
	
	@Autowired
	private CsvServiceStatistic csvServiceStatistic;
	
	@Autowired
	private XlsxServiceStatistic xlsxServiceStatistic;

	private static final Logger LOG = Logger.getLogger(StatisticController.class);

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	@ApiOperation(value = "Get short statistic", notes = "Get short statistic by survey id", tags = "Statistic")
	@RequestMapping(value = "/{survey_id}", method = RequestMethod.GET)
	public ResponseEntity<SurveyStatisticShort> surveyStatistic(@PathVariable(value = "survey_id") Integer surveyId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {
			return new ResponseEntity<SurveyStatisticShort>(statisticService.survey(surveyId), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Short statistic" + e.getMessage());
			return new ResponseEntity<SurveyStatisticShort>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@ApiOperation(value = "Export statistic on google sheets", notes = "Export statistic on google sheets by survey id", tags = "Statistic")
	@RequestMapping(value = "/{survey_id}/", method = RequestMethod.POST)
	public ResponseEntity<Object> exportSurveyStatistic(@PathVariable("survey_id") Integer surveyId,
			@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {
			Map<String, String> responseValue = new HashMap<String, String>();
			responseValue.put("URL", statisticService.export(surveyId));
			return new ResponseEntity<Object>(responseValue, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Export statistic" + e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Export statistic about surveys to JSON file
	 * @return 
	 */
	@ApiOperation(value = "Export statistic to JSON file", notes = "Export all statistic to local JSON file", tags = "Statistic")
	@RequestMapping(value = "/datajson", method = RequestMethod.POST)
	public ResponseEntity<Object> exportSurveyStatisticJson(@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			Map<String, File> responseValue = new HashMap<String, File>();
			responseValue.put("File", jsonServiceStatistic.export());
			return new ResponseEntity<Object>(responseValue, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Export statistic" + e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Export statistic about surveys to Csv file
	 * @return 
	 */
	@ApiOperation(value = "Export statistic to CSV file", notes = "Export all statistic to local CSV file", tags = "Statistic")
	@RequestMapping(value = "/datacsv", method = RequestMethod.POST)
	public ResponseEntity<Object> exportSurveyStatisticCsv(/*@RequestHeader String token*/) {

		/*if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}*/
		
		try {
			Map<String, File> responseValue = new HashMap<String, File>();
			responseValue.put("File", csvServiceStatistic.export());
			return new ResponseEntity<Object>(responseValue, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Export statistic" + e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Export statistic about surveys to XML file
	 * @return 
	 */
	@ApiOperation(value = "Export statistic to XML file", notes = "Export all statistic to local XML file", tags = "Statistic")
	@RequestMapping(value = "/dataxml", method = RequestMethod.POST)
	public ResponseEntity<Object> exportSurveyStatisticXml(@RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			Map<String, File> responseValue = new HashMap<String, File>();
			responseValue.put("File", xmlServiceStatistic.export());
			return new ResponseEntity<Object>(responseValue, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Export statistic" + e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Export statistic about surveys to XLSX file
	 * @return 
	 */
	@ApiOperation(value = "Export statistic to XLSX file", notes = "Export all statistic to local XLSX file", tags = "Statistic")
	@RequestMapping(value = "/dataxlsx", method = RequestMethod.POST)
	public ResponseEntity<Object> exportSurveyStatisticXlsx(/*@RequestHeader String token*/) {

		/*if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}*/
		
		try {
			Map<String, File> responseValue = new HashMap<String, File>();
			responseValue.put("File", xlsxServiceStatistic.export());
			return new ResponseEntity<Object>(responseValue, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Export statistic" + e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
