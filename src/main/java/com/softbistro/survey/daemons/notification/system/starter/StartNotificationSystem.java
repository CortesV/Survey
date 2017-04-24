package com.softbistro.survey.daemons.notification.system.starter;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softbistro.survey.daemons.notification.system.service.NotificationService;

/**
 * Starting NotificationSystem
 *
 */
@Component
@EnableScheduling
public class StartNotificationSystem {
	
	private Logger log = LogManager.getLogger(getClass());

	@Resource
	private ApplicationContext context;

	@Scheduled(fixedRate = 5000)
	public void test() {

		Thread registrationThread = new Thread(context.getBean(NotificationService.class));

		registrationThread.start();
		log.info("Notification system thread: " + registrationThread.getName());

	}
}
