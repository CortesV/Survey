package com.softbistro.survey.notification.system.component.entity;

/**
 * Entity for structure of email about client
 * 
 * @author zviproject
 *
 */
public class RegistrationMessage {
	private Integer clientId;
	private String clientName;
	private String clientEmail;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

}
