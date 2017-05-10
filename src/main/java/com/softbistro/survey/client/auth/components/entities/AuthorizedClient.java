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
	private String token;

	/**
	 * timeValidKey - time of life uniqueKey in minutes
	 */
	private Integer timeValidKey;

	public AuthorizedClient() {

	}

	public AuthorizedClient(String token, String clientId, Integer timeValidKey) {

		this.clientId = clientId;
		this.token = token;
		this.timeValidKey = timeValidKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	@Override
	public String toString() {
		return "AuthorizedClient [clientId=" + clientId + ", uniqueKey=" + token + ", timeValidKey=" + timeValidKey
				+ "]";
	}

}
