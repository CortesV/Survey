package com.softbistro.survey.imports.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softbistro.survey.imports.system.interfaces.IImportSurvey;

/**
 * Controller for import survey
 * 
 * @author olegnovatskiy
 */
@RestController
@RequestMapping("/rest/survey/v1.0/importsurvey")
public class ImportSurveyController {

	@Autowired
	private IImportSurvey iImportSurvey;
	
	/**
	 * Importing survey from selected file of csv type to datadase.
	 * 
	 * @param request
	 * @param clientId
	 * @return Response
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public ResponseEntity<Object> importSurvey(HttpServletRequest request, @RequestParam(name = "clientId") Long clientId) {

		return iImportSurvey.importSyrveyCSV(request, clientId);
	}

	

}
