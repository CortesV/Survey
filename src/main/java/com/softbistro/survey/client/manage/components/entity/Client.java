package com.softbistro.survey.client.manage.components.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Simple JavaBean object that represents a Client
 * 
 * @author cortes
 * @version 1.0
 *
 */
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String socialKey;
	private String clientName;
	private String password;
	private String email;
	private Boolean activated;
	private List<AuthorityName> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean isActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public List<AuthorityName> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityName> authorities) {
		this.authorities = authorities;
	}

	public String getSocialKey() {
		return socialKey;
	}

	public void setSocialKey(String socialKey) {
		this.socialKey = socialKey;
	}

	public Boolean getActivated() {
		return activated;
	}
}
