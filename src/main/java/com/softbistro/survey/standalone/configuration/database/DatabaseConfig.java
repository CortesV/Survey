package com.softbistro.survey.standalone.configuration.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

	@Bean(name = "dsNotificationSystem")
	@ConfigurationProperties(prefix = "spring.ds_notification")
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcNotificationSystem")
	@Autowired
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("dsNotificationSystem") DataSource dsPostgres) {
		return new JdbcTemplate(dsPostgres);
	}
	
	@Primary	
	@Bean(name = "dsSurvey")
	@ConfigurationProperties(prefix = "spring.ds_survey")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "jdbcSurvey")
	public JdbcTemplate jdbcTemplate(@Qualifier("dsSurvey") DataSource dsSurvey) {
		return new JdbcTemplate(dsSurvey);
	}

}
