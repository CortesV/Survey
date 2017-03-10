package com.survey.softbistro.components.entity;

import java.util.List;

public class Authority {

	private Long id;

	private AuthorityName name;

	private List<Client> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthorityName getName() {
		return name;
	}

	public void setName(AuthorityName name) {
		this.name = name;
	}

	public List<Client> getUsers() {
		return users;
	}

	public void setUsers(List<Client> users) {
		this.users = users;
	}
}