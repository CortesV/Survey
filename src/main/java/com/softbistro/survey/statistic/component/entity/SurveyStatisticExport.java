package com.softbistro.survey.statistic.component.entity;

import java.sql.Date;
import java.util.List;

/**
 * Information about survey for export
 * 
 * @author zviproject
 *
 */
public class SurveyStatisticExport {
	private Integer id;
	private String name;
	private Integer participantId;
	private String firstName;
	private String lastName;
	private String groupName;
	private String questionName;
	private String answer;
	private String comment;
	private Date answerDateTime;
	private List<ParticipantAttributes> participantAttribute;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getAnswerDateTime() {
		return answerDateTime;
	}

	public void setAnswerDateTime(Date answerDateTime) {
		this.answerDateTime = answerDateTime;
	}

	public List<ParticipantAttributes> getParticipantAttribute() {
		return participantAttribute;
	}

	public void setParticipantAttribute(List<ParticipantAttributes> participantAttribute) {
		this.participantAttribute = participantAttribute;
	}

}
