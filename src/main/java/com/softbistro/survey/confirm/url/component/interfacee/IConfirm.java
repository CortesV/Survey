package com.softbistro.survey.confirm.url.component.interfacee;

import com.softbistro.survey.response.Response;

/**
 * Using for confirm operations from notification system
 * 
 * @author zviproject
 *
 */
public interface IConfirm {

	/**
	 * Confirming change user password by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	public Response confirmPassword(String uuid);

	/**
	 * Confirming email new client by using update status in database
	 * 
	 * @param uuid
	 * @return
	 */
	public Response confirmEmail(String uuid);
}
