package com.survey.softbistro.components.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Return response status of execution request
 * 
 * @author cortes
 *
 */
public class ResponseStatus {

	private static final String KEY_RESPONSE_STATUS = "responseStatus";
	private static final String SUCCESSFUL_RESPONSE_STATUS = "Successfull";
	private static final String FAILED_RESPONSE_STATUS = "Unsuccessfull";

	/**
	 * Method that return status of execution sql request
	 * 
	 * @param status
	 * @return
	 */
	public static Map<String, String> returnResponseStatus(int status) {

		Map<String, String> response = new HashMap<>();
		if (status > 0) {
			response.put(KEY_RESPONSE_STATUS, SUCCESSFUL_RESPONSE_STATUS);
			return response;
		} else {
			response.put(KEY_RESPONSE_STATUS, FAILED_RESPONSE_STATUS);
			return response;
		}
	}
}
