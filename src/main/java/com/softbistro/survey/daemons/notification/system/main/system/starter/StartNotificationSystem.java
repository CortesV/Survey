package com.softbistro.survey.daemons.notification.system.main.system.starter;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softbistro.survey.daemons.notification.system.main.system.component.entity.Notification;
import com.softbistro.survey.daemons.notification.system.main.system.component.interfaces.INotification;
import com.softbistro.survey.daemons.notification.system.main.system.threads.NotificationService;
import com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces.IRetryNotification;

/**
 * Starting NotificationSystem
 */
@Component
@EnableScheduling
public class StartNotificationSystem {
	@Resource
	private ApplicationContext context;

	@Autowired
	private INotification iSendingMessage;

	@Autowired
	private IRetryNotification iRetryNotification;

	@Autowired
	private Properties propertiesSurvey;

	@Value("${count.of.records}")
	private int countOfRecords;

	/**
	 * Every 1 minute (60 seconds) start thread, which checking the database if
	 * there are new messages. If there is - creates threads (or thread) to work
	 * with them, if not - then nothing.
	 */
	@Scheduled(fixedRate = 60 * 1000)
	public void CheckThread() {

		List<Notification> messages = iSendingMessage.getAllEmailsToSending();

		if (messages.isEmpty()) {
			return;
		}

		iSendingMessage.updateStatusMessagesToInProcess();

		int upperIndexOfEmailForThread = countOfRecords;

		for (int i = 0; i < messages.size(); i = i
				+ countOfRecords, upperIndexOfEmailForThread = upperIndexOfEmailForThread + countOfRecords) {

			if (upperIndexOfEmailForThread > messages.size()) {
				upperIndexOfEmailForThread = messages.size();
			}

			new Thread(new NotificationService(messages.subList(i, upperIndexOfEmailForThread), iSendingMessage,
					iRetryNotification, propertiesSurvey)).start();

		}

	}
}
