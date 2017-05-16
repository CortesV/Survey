package com.softbistro.survey.client.manage.components.entity;

/**
 * Entity for getting new clients and clients that update password
 * @author alex_alokhin
 *
 */
public class ClientForSending {

	private Integer id;
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ClientForSending(Integer id, String email) {
		this.id = id;
		this.email = email;
	}
}
