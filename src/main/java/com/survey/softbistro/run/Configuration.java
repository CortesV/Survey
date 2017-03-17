package com.survey.softbistro.run;

import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class Configuration {
	// @Autowired
	// private org.springframework.core.env.Environment environment;
	//
	// @Bean
	// public Properties properties() {
	// Properties props = new Properties();
	// props.put("smtp.gmail.com",
	// environment.getRequiredProperty("mail.smtp.host"));
	// props.put("mail.smtp.socketFactory.port",
	// environment.getRequiredProperty("mail.smtp.socketFactory.port"));
	// props.put("mail.smtp.socketFactory.class",
	// environment.getRequiredProperty("mail.smtp.socketFactory.class"));
	// props.put("mail.smtp.auth",
	// environment.getRequiredProperty("mail.smtp.auth"));
	// props.put("mail.smtp.port",
	// environment.getRequiredProperty("mail.smtp.port"));
	// return props;
	// }

}
