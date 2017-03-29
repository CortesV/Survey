package com.softbistro.survey.response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Response template
 * 
 * 
 * @author cortes
 *
 */
@Component
public class Response {

	/**
	 * Object which work in request
	 */
	private Object data;

	/**
	 * Return http code from WEB page
	 */
	private Integer responseCode;

	/**
	 * Status operation on request
	 */
	private HttpStatus responseStatus;

	/**
	 * Return discription for operation or return error when operation field
	 */
	private String responseDescription;

	public Response(Object data, HttpStatus responseStatus, String responseDescription) {

		this.data = data;
		this.responseCode = responseStatus.value();
		this.responseStatus = responseStatus;
		this.responseDescription = responseDescription;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public HttpStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(HttpStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

}
