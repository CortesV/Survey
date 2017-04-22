package com.softbistro.survey.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.softbistro.survey" })
public class SurveySoftBistroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveySoftBistroApplication.class, args);
	}
}
