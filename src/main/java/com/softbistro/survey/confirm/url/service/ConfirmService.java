package com.softbistro.survey.confirm.url.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.confirm.url.component.interfacee.IConfirm;
import com.softbistro.survey.response.Response;

@Service
public class ConfirmService {
	@Autowired
	private IConfirm iConfirm;

	/**
	 * Confirming change user password by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	public Response confirmPassword(String uuid) {
		return iConfirm.confirmPassword(uuid);
	}

	/**
	 * Confirming email new client by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	public Response confirmEmail(String uuid) {
		return iConfirm.confirmEmail(uuid);
	}
}
