package com.softbistro.survey.daemons.notification.system.component.entity;

import java.util.Date;

/**
 * Entity for sending_client table
 * 
 * @author alex_alokhin
 *
 */
public class NotificationClientSending {

	private String url;
	private Integer clientId;
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public NotificationClientSending(String url, Integer clientId) {
		this.url = url;
		this.clientId = clientId;
	}

	public NotificationClientSending(String url, Integer clientId, Date date) {

		this.url = url;
		this.clientId = clientId;
		this.date = date;
	}

}
