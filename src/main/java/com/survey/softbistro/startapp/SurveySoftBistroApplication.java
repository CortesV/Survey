package com.survey.softbistro.startapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
//@ComponentScan("com.survey.softbistro")
@ComponentScan(basePackages = { "com.survey.softbistro" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
public class SurveySoftBistroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveySoftBistroApplication.class, args);
	}
}
