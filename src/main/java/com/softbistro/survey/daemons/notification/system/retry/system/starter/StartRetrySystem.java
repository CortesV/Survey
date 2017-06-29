package com.softbistro.survey.daemons.notification.system.retry.system.starter;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softbistro.survey.daemons.notification.system.retry.system.component.entity.RetryNotification;
import com.softbistro.survey.daemons.notification.system.retry.system.component.interfaces.IRetryNotification;
import com.softbistro.survey.daemons.notification.system.retry.system.threads.RetryNotificationService;

/**
 * Starting Retry notification system
 */
@Component
@EnableScheduling
public class StartRetrySystem {
	@Autowired
	private IRetryNotification iRetryNotification;

	@Autowired
	private Properties propertiesSurvey;

	@Value("${count.of.records}")
	private int countOfRecords;

	/**
	 * Every 3 minutes (180 seconds) start thread, which checking the database
	 * if there are messages with status "ERROR". If there is - creates threads
	 * (or thread) to work with them, if not - then nothing.
	 */
	@Scheduled(fixedRate = 180 * 1000)
	public void RetryCheckThread() {

		List<RetryNotification> retryMessages = iRetryNotification.getAllErrorEmailsToResending();

		if (retryMessages.isEmpty()) {
			return;
		}

		iRetryNotification.updateIncreaseRetryCountForMessageToResend();
		iRetryNotification.updateStatusMessagesToInProcess();

		int upperIndexOfEmailForRetryThread = countOfRecords;

		for (int i = 0; i < retryMessages.size(); i = i
				+ countOfRecords, upperIndexOfEmailForRetryThread = upperIndexOfEmailForRetryThread + countOfRecords) {

			if (upperIndexOfEmailForRetryThread > retryMessages.size()) {
				upperIndexOfEmailForRetryThread = retryMessages.size();
			}

			new Thread(new RetryNotificationService(retryMessages.subList(i, upperIndexOfEmailForRetryThread),
					iRetryNotification, propertiesSurvey)).start();
		}

	}
}
