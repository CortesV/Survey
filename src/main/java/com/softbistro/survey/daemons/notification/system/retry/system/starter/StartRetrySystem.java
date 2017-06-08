package com.softbistro.survey.daemons.notification.system.retry.system.starter;

import java.util.ArrayList;
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
	private int countOfRetryThread;
	private int lowerIndexOfEmailForRetryThread;
	private int upperIndexOfEmailForRetryThread;

	@Autowired
	private IRetryNotification iRetryNotification;

	@Autowired
	private Properties propertiesSurvey;

	@Value("${count.of.records}")
	private int countOfRecords;

	@Scheduled(fixedRate = 120 * 1000)
	public void RetryCheckThread() {

		List<RetryNotification> retryMessages = iRetryNotification.getAllErrorEmailsToResending();

		if (retryMessages.isEmpty()) {
			return;
		}

		iRetryNotification.updateIncreaseRetryCountForMessageToResend();
		iRetryNotification.updateStatusMessagesToInProcess();

		countOfRetryThread = (int) Math.ceil((double) retryMessages.size() / countOfRecords);

		lowerIndexOfEmailForRetryThread = 0;
		upperIndexOfEmailForRetryThread = countOfRecords;

		for (int i = 0; i < countOfRetryThread; i++) {
			List<RetryNotification> retryMessagesForThread = new ArrayList<RetryNotification>();

			if (upperIndexOfEmailForRetryThread > retryMessages.size()) {
				upperIndexOfEmailForRetryThread = retryMessages.size();

			}

			for (int j = lowerIndexOfEmailForRetryThread; j < upperIndexOfEmailForRetryThread; j++) {
				retryMessagesForThread.add(retryMessages.get(j));
			}

			new Thread(new RetryNotificationService(retryMessagesForThread, iRetryNotification, propertiesSurvey))
					.start();

			lowerIndexOfEmailForRetryThread = lowerIndexOfEmailForRetryThread + countOfRecords;
			upperIndexOfEmailForRetryThread = upperIndexOfEmailForRetryThread + countOfRecords;
		}
	}
}
