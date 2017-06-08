package com.softbistro.survey.daemons.notification.system.main.system.starter;

import java.util.ArrayList;
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
	private int countOfThread;
	private int lowerIndexOfEmailForThread;
	private int upperIndexOfEmailForThread;

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

	@Scheduled(fixedRate = 60 * 1000)
	public void CheckThread() {

		List<Notification> messages = iSendingMessage.getAllEmailsToSending();

		iSendingMessage.updateStatusMessagesToInProcess();

		if (messages.isEmpty()) {
			return;
		}

		countOfThread = (int) Math.ceil((double) messages.size() / countOfRecords);

		lowerIndexOfEmailForThread = 0;
		upperIndexOfEmailForThread = countOfRecords;

		for (int i = 0; i < countOfThread; i++) {
			List<Notification> messagesForThread = new ArrayList<Notification>();

			if (upperIndexOfEmailForThread > messages.size()) {
				upperIndexOfEmailForThread = messages.size();

			}

			for (int j = lowerIndexOfEmailForThread; j < upperIndexOfEmailForThread; j++) {
				messagesForThread.add(messages.get(j));
			}

			new Thread(
					new NotificationService(messagesForThread, iSendingMessage, iRetryNotification, propertiesSurvey))
							.start();

			lowerIndexOfEmailForThread = lowerIndexOfEmailForThread + countOfRecords;
			upperIndexOfEmailForThread = upperIndexOfEmailForThread + countOfRecords;
		}

	}
}
