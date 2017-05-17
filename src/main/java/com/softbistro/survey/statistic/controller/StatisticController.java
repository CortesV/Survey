package com.softbistro.survey.statistic.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.client.auth.service.AuthorizationService;
import com.softbistro.survey.statistic.component.entity.SurveyStatisticShort;
import com.softbistro.survey.statistic.service.ExportFileService;
import com.softbistro.survey.statistic.service.StatisticService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for statistic
 * @author alex_alokhin
 *
 */
@RestController
@RequestMapping("rest/survey/v1/statistic")
public class StatisticController {

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private ExportFileService exportFileService;

	private static final Logger LOG = Logger.getLogger(StatisticController.class);

	/**
	 * Get answers on question from survey
	 * 
	 * @param surveyId
	 *            - survey id for getting information
	 * @return
	 */
	@ApiOperation(value = "Get short statistic", notes = "Get short statistic by survey id", tags = "Statistic")
	@RequestMapping(value = "/{survey_id}", method = RequestMethod.GET, produces = "application/json")
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
	@ApiOperation(value = "Export statistic on google sheets", notes = "Export statistic on google sheets by survey id "
			+ "and column filters", tags = "Statistic")
	@RequestMapping(value = "/{survey_id}/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> exportSurveyStatistic(@PathVariable("survey_id") Integer surveyId,
			@RequestBody List<String> filters, @RequestHeader String token) {

//		if (!authorizationService.checkAccess(token)) {
//
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}

		try {
			Map<String, String> responseValue = new HashMap<String, String>();
			responseValue.put("URL", statisticService.export(surveyId, filters));
			return new ResponseEntity<Object>(responseValue, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Export statistic" + e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Export statistic about surveys to file with specified extension
	 * @return 
	 */
	@ApiOperation(value = "Export statistic about surveys to file with specified extension", notes = "Export statistic about surveys to temporaty file", tags = "Statistic")
	@RequestMapping(value = "/data/{extension}", method = RequestMethod.POST)
	public ResponseEntity<Object> exportSurveyStatistic(@RequestHeader String token, @PathVariable("extension") String extension) {
 		
		if (!authorizationService.checkAccess(token)) {
 			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
 		}
		
		try {

 			Map<String, File> responseValue = new HashMap<String, File>();

 			responseValue.put("File", exportFileService.exportToFile(extension));

 			return new ResponseEntity<Object>(responseValue, HttpStatus.OK);

 		} catch (Exception e) {

 			LOG.error("Export statistic" + e.getMessage());

 			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

 		}
	}
	/**
	 * Export statistic about survey
	 * 
	 * @param surveyId
	 * @return
	 */
	@ApiOperation(value = "Get Statistic Filters for Export statistic on google sheets", notes = "Get column filters for export statistic on google sheets ", tags = "Statistic")
	@RequestMapping(value = "/filters/{survey_id}/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<String>> getStatisticFilters(@PathVariable("survey_id") Integer surveyId, @RequestHeader String token) {


//		if (!authorizationService.checkAccess(token)) {
//
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
		
		try {

			return new ResponseEntity<List<String>>(statisticService.getStatisticColumnFilters(surveyId), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Export statistic" + e.getMessage());
			return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
