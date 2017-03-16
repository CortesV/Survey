package com.softbistro.survey.response;

import org.springframework.http.HttpStatus;

/**
 * Response template
 * @author cortes
 *
 */
public class Response {

	private Object data;
	private Integer responseCode;
	private HttpStatus responseStatus;
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
		return responseStatus.value();
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
