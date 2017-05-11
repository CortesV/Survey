package com.softbistro.survey.statistic.component.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.softbistro.survey.daemons.notification.system.component.entity.Notification;

public class SurveyStatisticShortMapper implements RowMapper<SurveyStatisticShort>{

	@Override
	public SurveyStatisticShort mapRow(ResultSet rs, int rowNum) throws SQLException {
		return null;
	}

}
