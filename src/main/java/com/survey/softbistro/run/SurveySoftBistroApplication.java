package com.survey.softbistro.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.survey.softbistro.sending.gmail.service.EmailSender;

@SpringBootApplication
public class SurveySoftBistroApplication {

	private static EmailSender emailSender;

	public static void main(String[] args) {
		SpringApplication.run(SurveySoftBistroApplication.class, args);
		emailSender = new EmailSender();
		emailSender.send();
	}
}
