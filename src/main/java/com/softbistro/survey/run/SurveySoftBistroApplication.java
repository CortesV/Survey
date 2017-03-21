package com.softbistro.survey.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.softbistro.survey")
@PropertySource("classpath:application.properties")
public class SurveySoftBistroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveySoftBistroApplication.class, args);
	}
}
