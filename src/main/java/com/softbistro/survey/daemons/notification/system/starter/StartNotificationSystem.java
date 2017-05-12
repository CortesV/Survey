package com.softbistro.survey.daemons.notification.system.starter;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softbistro.survey.daemons.notification.system.service.NotificationService;

/**
 * Starting NotificationSystem
 */
@Component
@EnableScheduling
public class StartNotificationSystem {

	@Resource
	private ApplicationContext context;

	@Scheduled(fixedRate = 25000)
	public void CheckThread() {

		new Thread(context.getBean(NotificationService.class)).start();

	}
}
