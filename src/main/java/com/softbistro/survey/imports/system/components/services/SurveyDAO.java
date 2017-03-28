package com.softbistro.survey.imports.system.components.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.softbistro.survey.imports.system.components.entities.Survey;
import com.softbistro.survey.imports.system.components.interfaces.ISurvey;
import com.softbistro.survey.response.Response;

@Repository
public class SurveyDAO implements ISurvey {

	private static final String INSERT_SURVEY = " INSERT INTO surveyTest.survey (client_id, name) VALUES ( %d, '%s' ); ";
	private static final String INSERT_GROUP = "";
	private static final String INSERT_QUESTIONs = "";
	
	private JdbcTemplate jdbc;
	
	@Override
	public Response saveSurvay(Survey survey) {

		String insertQuery = String.format(INSERT_SURVEY, survey.getClienId(), survey.getTitle());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator preStatement = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				return con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			}
		};

		jdbc.update(preStatement, keyHolder);
		Integer generateIdSurvey = keyHolder.getKey().intValue();

		System.out.println(generateIdSurvey);
		
		return new Response(survey, HttpStatus.CREATED, "Survey is saved");
	}

	
	
}
