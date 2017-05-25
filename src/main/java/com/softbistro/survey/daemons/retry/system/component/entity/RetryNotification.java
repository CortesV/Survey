package com.softbistro.survey.daemons.retry.system.component.entity;

public class RetryNotification {
	private int id;
	private String senderEmail;
	private String senderPassword;
	private String senderDescription;
	private String receiverCCEmail;
	private String receiverEmail;
	private String header;
	private String body;
	private String textException;
	private int retryCount;

	public RetryNotification() {
	}

	public RetryNotification(int id, String textException) {
		this.id = id;
		this.textException = textException;
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

	public String getTextException() {
		return textException;
	}

	public void setTextException(String textException) {
		this.textException = textException;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
}
