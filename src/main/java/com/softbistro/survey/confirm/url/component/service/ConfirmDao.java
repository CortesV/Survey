package com.softbistro.survey.confirm.url.component.service;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.softbistro.survey.confirm.url.component.interfacee.IConfirm;

/**
 * Input information about operation from message in database
 * 
 * @author zviproject
 *
 */
@Repository
public class ConfirmDao implements IConfirm {

	private static final Logger LOGGER = Logger.getLogger(ConfirmDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String statusForConfirmPassword = "DONE";

	private final String statusForConfirmClient = "DONE";

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
	public void confirmPassword(String uuid) {

		try {

			Integer clientId = jdbcTemplate.queryForObject(SQL_GET_INFORMATION_BY_USING_UUID_PASSWORD, Integer.class,
					uuid, date);
			jdbcTemplate.update(SQL_UPDATE_STATUS_FOR_CONFIRMING_CLIENT_OPERATIONS, statusForConfirmPassword, clientId);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Confirming email new client by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public void confirmEmail(String uuid) {

		try {

			Integer clientId = jdbcTemplate.queryForObject(SQL_GET_INFORMATION_BY_USING_UUID_CLIENT, Integer.class,
					uuid, date);
			jdbcTemplate.update(SQL_UPDATE_STATUS_FOR_CONFIRMING_CLIENT_OPERATIONS, statusForConfirmClient, clientId);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
	}

}
