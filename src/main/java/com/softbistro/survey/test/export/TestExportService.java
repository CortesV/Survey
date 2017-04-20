package com.softbistro.survey.test.export;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for testing dynamic statistic export
 * 
 * @author af150416
 *
 */
@Service
public class TestExportService {

	@Autowired
	ITestExport iTestExport;

	/**
	 * Method for getting dynamic statistic
	 */
	public List<Map<String, Object>> testExport(List<String> filters, Integer id) {
		return iTestExport.testExport(filters, id);
	}
}
