package com.softbistro.survey.participant.components.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Class that represent participant entity
 * 
 * @author af150416
 *
 */
public class Participant implements Serializable {

	/**
	 * standard value for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * participant id
	 */
	private Integer id;

	/**
	 * participant first name
	 */
	private String firstName;

	/**
	 * participant last name
	 */
	private String lastName;

	/**
	 * participant e-mail
	 */
	private String eMail;

	/**
	 * participant password
	 */
	private String password;

	/**
	 * participant created date
	 */
	private Date createdDate;

	/**
	 * participant modified date
	 */
	private Date modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
