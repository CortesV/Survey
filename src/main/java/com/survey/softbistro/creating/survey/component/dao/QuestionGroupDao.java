package com.survey.softbistro.creating.survey.component.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionGroupDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

}
