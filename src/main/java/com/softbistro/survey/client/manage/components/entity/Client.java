package com.softbistro.survey.client.manage.components.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Simple JavaBean object that represents a Client
 * 
 * @author cortes
 * @version 1.0
 *
 */
@JsonInclude(Include.NON_NULL)
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String facebookId;
	private String googleId;
	private String clientName;
	private String password;
	private String email;
	/**
	 * This field that represent authorization token
	 */
	private String token;
	/**
	 * This field identify of network from which client is authorized
	 */
	private String flag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
