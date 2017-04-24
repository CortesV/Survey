package com.softbistro.survey.standalone.configuration.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseNotificationSystemConfig {

	@Bean(name = "dsNotificationSystem")
	@ConfigurationProperties(prefix = "spring.ds_notification")
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcNotificationSystem")
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("dsNotificationSystem") DataSource dsPostgres) {
		return new JdbcTemplate(dsPostgres);
	}

}
