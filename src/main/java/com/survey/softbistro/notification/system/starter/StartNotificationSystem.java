package com.survey.softbistro.notification.system.starter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.survey.softbistro.notification.system.service.ChangePasswordMessageService;
import com.survey.softbistro.notification.system.service.RegistrationMessageServise;
import com.survey.softbistro.notification.system.service.SurveyMessageService;

@Component
@EnableScheduling
public class StartNotificationSystem {

	private Logger log = LogManager.getLogger(getClass());

	@Autowired
	RegistrationMessageServise registrationMessageServise;
	@Autowired
	SurveyMessageService surveyMessageService;
	@Autowired
	ChangePasswordMessageService changePasswordMessageService;

	@Scheduled(fixedRate = 10000)
	public void test() {
		Thread registrationThread = new Thread(registrationMessageServise);
		Thread surveyThread = new Thread(surveyMessageService);
		Thread passwordThread = new Thread(changePasswordMessageService);

		registrationThread.start();
		surveyThread.start();
		passwordThread.start();
	}

}
