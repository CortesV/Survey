package com.softbistro.survey.notification.system.starter;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softbistro.survey.notification.system.service.ChangePasswordMessageService;
import com.softbistro.survey.notification.system.service.RegistrationMessageServise;
import com.softbistro.survey.notification.system.service.SurveyMessageService;

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

	@Resource
	private ApplicationContext context;

<<<<<<< HEAD:src/main/java/com/survey/softbistro/notification/system/starter/StartNotificationSystem.java
	@Scheduled(fixedRate = 500000)
=======
    //@Scheduled(fixedRate = 5000)
>>>>>>> 61b3f919c64e2ad59f500faeec5fa96576ba436f:src/main/java/com/softbistro/survey/notification/system/starter/StartNotificationSystem.java
	public void test() {

		Thread registrationThread = new Thread(context.getBean(RegistrationMessageServise.class));
		Thread surveyThread = new Thread(context.getBean(SurveyMessageService.class));
		Thread passwordThread = new Thread(context.getBean(ChangePasswordMessageService.class));

		registrationThread.start();
		log.info("Registration thread" + registrationThread.getName());

		surveyThread.start();
		log.info("Survey thread" + surveyThread.getName());

		passwordThread.start();
		log.info("Password thread" + passwordThread.getName());
	}

}