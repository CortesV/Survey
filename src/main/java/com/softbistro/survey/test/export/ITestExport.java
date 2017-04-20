package com.softbistro.survey.test.export;

import java.util.List;
import java.util.Map;

/**
 * Interface for testing dynamic export system
 * 
 * @author af150416
 *
 */
public interface ITestExport {

	/**
	 * Method for getting dynamic statistic
	 */
	public List<Map<String, Object>> testExport(List<String> filters, Integer id);
}
