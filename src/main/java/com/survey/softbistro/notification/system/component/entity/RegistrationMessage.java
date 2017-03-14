package com.survey.softbistro.notification.system.component.entity;

/**
 * Entity for structure of email about client
 * 
 * @author zviproject
 *
 */
public class RegistrationMessage {
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

}
