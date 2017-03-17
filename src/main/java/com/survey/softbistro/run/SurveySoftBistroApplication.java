package com.survey.softbistro.run;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.survey.softbistro")
@PropertySource("classpath:application.properties")
public class SurveySoftBistroApplication {

	@Autowired
	private org.springframework.core.env.Environment environment;

	@Bean
	public Properties propertiesClient() {
		Properties props = new Properties();
		props.put("mail.smtp.host", environment.getRequiredProperty("client.mail.smtp.host"));
		props.put("mail.smtp.socketFactory.port",
				environment.getRequiredProperty("client.mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class",
				environment.getRequiredProperty("client.mail.smtp.socketFactory.class"));
		props.put("mail.smtp.auth", environment.getRequiredProperty("client.mail.smtp.auth"));
		props.put("mail.smtp.port", environment.getRequiredProperty("client.mail.smtp.port"));
		return props;
	}

	@Bean
	public Properties propertiesPassword() {
		Properties props = new Properties();
		props.put("mail.smtp.host", environment.getRequiredProperty("password.mail.smtp.host"));
		props.put("mail.smtp.socketFactory.port",
				environment.getRequiredProperty("password.mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class",
				environment.getRequiredProperty("password.mail.smtp.socketFactory.class"));
		props.put("mail.smtp.auth", environment.getRequiredProperty("password.mail.smtp.auth"));
		props.put("mail.smtp.port", environment.getRequiredProperty("password.mail.smtp.port"));
		return props;
	}

	@Bean
	public Properties propertiesSurvey() {
		Properties props = new Properties();
		props.put("mail.smtp.host", environment.getRequiredProperty("survey.mail.smtp.host"));
		props.put("mail.smtp.socketFactory.port",
				environment.getRequiredProperty("survey.mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class",
				environment.getRequiredProperty("survey.mail.smtp.socketFactory.class"));
		props.put("mail.smtp.auth", environment.getRequiredProperty("survey.mail.smtp.auth"));
		props.put("mail.smtp.port", environment.getRequiredProperty("survey.mail.smtp.port"));
		return props;
	}

	public static void main(String[] args) {

		SpringApplication.run(SurveySoftBistroApplication.class, args);
	}
}
