package com.survey.softbistro.notification.system.starter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.survey.softbistro.notification.system.component.interfacee.ISendingMessage;
import com.survey.softbistro.notification.system.service.ChangePasswordMessageService;
import com.survey.softbistro.notification.system.service.RegistrationMessageServise;
import com.survey.softbistro.notification.system.service.SurveyMessageService;

/**
 * Starting NotificationSystem in three thread 1- checking registration<br>
 * 2- checking new surveys <br>
 * 3 - checiking changed passwords
 * 
 * @author zviproject
 *
 */
@Component
@EnableScheduling
public class StartNotificationSystem {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	ISendingMessage iSendingMessage;

	@Scheduled(fixedRate = 10000)
	public void test() {
		Thread registrationThread = new Thread(new RegistrationMessageServise(iSendingMessage));
		Thread surveyThread = new Thread(new SurveyMessageService(iSendingMessage));
		Thread passwordThread = new Thread(new ChangePasswordMessageService(iSendingMessage));

		registrationThread.start();
		log.info("Registration thread" + registrationThread.getName());

		surveyThread.start();
		log.info("Survey thread" + registrationThread.getName());

		passwordThread.start();
		log.info("Password thread" + registrationThread.getName());
		System.out.println("=========================");
	}

}
