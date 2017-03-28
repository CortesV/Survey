package com.softbistro.survey.client.auth.components.entities;

import java.io.Serializable;

/**
 * Java class that use for cash and represent authorized client in system
 * 
 * @author cortes
 *
 */
public class AuthorizedClient implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * clientId - id client in MySQL database
	 */
	private String clientId;
	/**
	 * uniqueKey - UUID key that represent token for authorized client
	 */
	private String uniqueKey;

	/**
	 * timeValidKey - time of life uniqueKey in minutes
	 */
	private Integer timeValidKey;

	public AuthorizedClient() {

	}

	public AuthorizedClient(String uniqueKey, String clientId, Integer timeValidKey) {

		this.clientId = clientId;
		this.uniqueKey = uniqueKey;
		this.timeValidKey = timeValidKey;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Integer getTimeValidKey() {
		return timeValidKey;
	}

	public void setTimeValidKey(Integer timeValidKey) {
		this.timeValidKey = timeValidKey;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
