package com.survey.softbistro.response;

/**
 * Response with detail about object
 * 
 * @author zviproject
 *
 */
public class Response {
	private Status status;
	private Integer idObject;

	public Response(Integer idObject, Status status) {
		this.idObject = idObject;
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public Integer getIdObject() {
		return idObject;
	}

}
