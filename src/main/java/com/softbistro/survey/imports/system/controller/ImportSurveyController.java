package com.softbistro.survey.imports.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.softbistro.survey.imports.system.services.ImportSurveyService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for import survey
 * 
 * @author olegnovatskiy
 */
@RestController
@RequestMapping("/rest/survey/v1/import")
public class ImportSurveyController {

	private static final Logger LOGGER = Logger.getLogger(ImportSurveyController.class);

	@Autowired
	private ImportSurveyService importSurveyService;

	@Autowired
	private AuthorizationService authorizationService;

	/**
	 * Importing survey from selected file of csv type to datadase.
	 * 
	 * @param request
	 * @param clientId
	 * @return Response
	 */
	@ApiOperation(value = "Import Survey", notes = "Import survey from file", tags = "Import")
	@RequestMapping(value = "/survey/{client_id}", method = RequestMethod.POST)
	public ResponseEntity<Object> importSurvey(HttpServletRequest request,
			@PathVariable(name = "client_id") Integer clientId, @RequestHeader String token) {

		if (!authorizationService.checkAccess(token)) {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {
			Map<String, Integer> responseSurveyId = new HashMap<>();
			responseSurveyId.put("surveyId", importSurveyService.importFile(request, clientId));
			return new ResponseEntity<>(responseSurveyId, HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
