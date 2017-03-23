package com.softbistro.survey.confirm.survey.component.dao;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.confirm.survey.component.interfacee.IConfirm;
import com.softbistro.survey.response.Response;

/**
 * Input information about operation from message in database
 * 
 * @author zviproject
 *
 */
@Repository
public class ConfirmDao implements IConfirm {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String statusForConfirmPassword = "DONE";

	private String statusForConfirmClient = "DONE";

	private String statusForConfirmSurvey = "DONE";

	/**
	 * Current time for comparison with working time of url
	 */
	private Date date = new Date(System.currentTimeMillis());

	private static final String SQL_GET_INFORMATION_BY_USING_UUID_PASSWORD = "SELECT client_id FROM sending_password "
			+ "WHERE url=? AND working_time > ?";

	private static final String SQL_GET_INFORMATION_BY_USING_UUID_CLIENT = "SELECT client_id FROM sending_client "
			+ "WHERE url=? AND working_time > ?";

	private static final String SQL_UPDATE_STATUS_FOR_CONFIRMING_CLIENT_OPERATIONS = "UPDATE clients SET `status`= ? WHERE id=?";

	/**
	 * Confirming change user password by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public Response confirmPassword(String uuid) {
		try {
			Integer clientId = jdbcTemplate.queryForObject(SQL_GET_INFORMATION_BY_USING_UUID_PASSWORD, Integer.class,
					uuid, date);
			jdbcTemplate.update(SQL_UPDATE_STATUS_FOR_CONFIRMING_CLIENT_OPERATIONS, statusForConfirmPassword, clientId);
			return new Response(null, HttpStatus.OK, null);
		} catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	/**
	 * Confirming email new client by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public Response confirmEmail(String uuid) {
		try {
			Integer clientId = jdbcTemplate.queryForObject(SQL_GET_INFORMATION_BY_USING_UUID_CLIENT, Integer.class,
					uuid, date);
			jdbcTemplate.update(SQL_UPDATE_STATUS_FOR_CONFIRMING_CLIENT_OPERATIONS, statusForConfirmClient, clientId);
			return new Response(null, HttpStatus.OK, null);
		} catch (Exception e) {
			return new Response(null, HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

}
