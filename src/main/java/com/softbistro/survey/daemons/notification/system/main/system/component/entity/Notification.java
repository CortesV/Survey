package com.softbistro.survey.daemons.notification.system.main.system.component.entity;

public class Notification {
	private int id;
	private String senderEmail;
	private String senderPassword;
	private String senderDescription;
	private String receiverCCEmail;
	private String receiverEmail;
	private String header;
	private String body;

	public Notification(String senderEmail, String receiverEmail, String header, String body) {
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.header = header;
		this.body = body;
	}

	public Notification(String senderEmail, String senderPassword, String senderDescription, String receiverCCEmail,
			String receiverEmail, String header, String body) {
		this.senderEmail = senderEmail;
		this.senderPassword = senderPassword;
		this.senderDescription = senderDescription;
		this.receiverCCEmail = receiverCCEmail;
		this.receiverEmail = receiverEmail;
		this.header = header;
		this.body = body;
	}

	public Notification() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}

	public String getSenderDescription() {
		return senderDescription;
	}

	public void setSenderDescription(String senderDescription) {
		this.senderDescription = senderDescription;
	}

	public String getReceiverCCEmail() {
		return receiverCCEmail;
	}

	public void setReceiverCCEmail(String receiverCCEmail) {
		this.receiverCCEmail = receiverCCEmail;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
