package com.softbistro.survey.daemons.notification.system.component.entity;

public class Notification {
	private String senderEmail;
	private String receiverCCEmail;
	private String receiverEmail;
	private String header;
	private String body;

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
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
