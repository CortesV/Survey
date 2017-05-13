package com.softbistro.survey.daemons.notification.system.component.entity;

public class NotificationClientSending {

	private String url;
	private Integer clientId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public NotificationClientSending(String url, Integer clientId) {
		super();
		this.url = url;
		this.clientId = clientId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
}
