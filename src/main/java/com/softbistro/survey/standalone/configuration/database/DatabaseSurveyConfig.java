package com.softbistro.survey.standalone.configuration.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseSurveyConfig {

	@Bean(name = "dsSurvey")
	@Primary
	@ConfigurationProperties(prefix = "spring.ds_survey")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcSurvey")
	public JdbcTemplate jdbcTemplate(@Qualifier("dsSurvey") DataSource dsSurvey) {
		return new JdbcTemplate(dsSurvey);
	}

}
