package com.softbistro.survey.daemons.notification.system.component.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * Need for getting notification information to send email
 * 
 */
public class NotificationRowMapper implements RowMapper<Notification> {

		@Override
		public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Notification(rs.getString(1),rs.getString(2),rs.getString(3),
					rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
		}
		
}
