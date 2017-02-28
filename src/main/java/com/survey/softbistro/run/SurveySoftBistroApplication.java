package com.survey.softbistro.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.survey.softbistro")
@PropertySource("classpath:application.properties")
public class SurveySoftBistroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveySoftBistroApplication.class, args);

	}
}
