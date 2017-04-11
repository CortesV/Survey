package com.softbistro.survey.statistic.component.entity;

import java.sql.Date;

/**
 * Short information about survey<br>
 * 
 * 
 * @author zviproject
 *
 */
public class SurveyStatisticShort {

	private Integer id;

	private String name;

	private Date startTimeOfSurvey;

	private Date finishTimeOfSurvey;

	private Integer participantCount;

	private Integer participantVoted;

	private Integer participanNotVoted;

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

	public Date getStartTimeOfSurvey() {
		return startTimeOfSurvey;
	}

	public void setStartTimeOfSurvey(Date startTimeOfSurvey) {
		this.startTimeOfSurvey = startTimeOfSurvey;
	}

	public Date getFinishTimeOfSurvey() {
		return finishTimeOfSurvey;
	}

	public void setFinishTimeOfSurvey(Date finishTimeOfSurvey) {
		this.finishTimeOfSurvey = finishTimeOfSurvey;
	}

	public Integer getParticipantCount() {
		return participantCount;
	}

	public void setParticipantCount(Integer participantCount) {
		this.participantCount = participantCount;
	}

	public Integer getParticipantVoted() {
		return participantVoted;
	}

	public void setParticipantVoted(Integer participantVoted) {
		this.participantVoted = participantVoted;
	}

	public Integer getParticipanNotVoted() {
		return participanNotVoted;
	}

	public void setParticipanNotVoted(Integer participanNotVoted) {
		this.participanNotVoted = participanNotVoted;
	}

}
