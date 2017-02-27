package com.survey.softbistro.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.survey.softbistro.sending.gmail.EmailSender;

@SpringBootApplication
public class SurveySoftBistroApplication {

	private static EmailSender emailSender;

	public static void main(String[] args) {
		SpringApplication.run(SurveySoftBistroApplication.class, args);
		emailSender = new EmailSender("zarovni03@gmail.com", "19991904");
		emailSender.send("Test", "hello)", "zarovni03@gmail.com", "vitya.semenyuk1995@gmail.com");
	}
}
