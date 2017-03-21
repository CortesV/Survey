package com.softbistro.survey.startapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = { "com.softbistro.survey" })
@PropertySource("classpath:application.properties")
public class SurveySoftBistroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveySoftBistroApplication.class, args);
	}
}
