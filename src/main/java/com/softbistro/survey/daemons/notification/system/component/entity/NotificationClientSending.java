package com.softbistro.survey.daemons.notification.system.component.entity;

/**
 * Entity for sending_client table
 * @author alex_alokhin
 *
 */
public class NotificationClientSending {

	private String url;
	private Integer clientId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public NotificationClientSending(String url, Integer clientId) {
		this.url = url;
		this.clientId = clientId;
	}

}
