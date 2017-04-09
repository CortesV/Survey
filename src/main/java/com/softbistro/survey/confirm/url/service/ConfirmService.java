package com.softbistro.survey.confirm.url.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.confirm.url.component.interfacee.IConfirm;

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
	public void confirmPassword(String uuid) {
		
		iConfirm.confirmPassword(uuid);
	}

	/**
	 * Confirming email new client by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	public void confirmEmail(String uuid) {
		
		iConfirm.confirmEmail(uuid);
	}
}
