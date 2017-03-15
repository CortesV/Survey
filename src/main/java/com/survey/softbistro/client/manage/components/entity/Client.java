package com.survey.softbistro.client.manage.components.entity;

import java.io.Serializable;
import java.sql.Date;
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
	private String clientName;
	private String password;
	private String email;
	private String status;
	private Boolean enabled;
	private List<AuthorityName> authorities;
	private Date lastPasswordResetDate;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<AuthorityName> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityName> authorities) {
		this.authorities = authorities;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

}
