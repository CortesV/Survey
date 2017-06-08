package com.softbistro.survey.statistic.component.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SurveyStatisticShortMapper implements RowMapper<SurveyStatisticShort> {

	@Override
	public SurveyStatisticShort mapRow(ResultSet rs, int rowNum) throws SQLException {
		SurveyStatisticShort shortSurvey = new SurveyStatisticShort();
		shortSurvey.setId(rs.getInt(1));
		shortSurvey.setName(rs.getString(2));
		shortSurvey.setStartTimeOfSurvey(rs.getDate(3));
		shortSurvey.setFinishTimeOfSurvey(rs.getDate(4));
		shortSurvey.setParticipantCount(rs.getInt(5));
		shortSurvey.setParticipantVoted(rs.getInt(6));

		shortSurvey.setParticipanNotVoted(rs.getInt(5) - rs.getInt(6));
		return shortSurvey;
	}

}
