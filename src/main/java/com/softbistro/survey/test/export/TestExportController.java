package com.softbistro.survey.test.export;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * Controller for testing dynamic statistic export
 * 
 * @author af150416
 *
 */
@RestController
@RequestMapping("/rest/survey/v1/test")
public class TestExportController {

	private static final Logger LOGGER = Logger.getLogger(TestExportController.class);

	@Autowired
	private TestExportService testExportService;

	/**
	 * Method for creating the attribute value
	 * 
	 * @param attributes
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Testing dynamic statistic export", notes = "Testing dynamic statistic export by id", tags = "Test")
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> testExport(@PathVariable("id") Integer id, @RequestBody List<String> filters) {

		try {

			return new ResponseEntity<>(testExportService.testExport(filters, id), HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
