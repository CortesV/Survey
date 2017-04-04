package com.softbistro.survey.startapp;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@org.springframework.context.annotation.Configuration
@EnableSwagger2
public class SwaggerConfig {
	// @Bean
	// public Docket api() {
	// return new Docket(DocumentationType.SWAGGER_2).select()
	// .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
	// .paths(PathSelectors.any()).build().apiInfo(metadata());
	// }

	// private ApiInfo metadata() {
	// return new ApiInfoBuilder().title("Survey
	// API").description("Documentation for Survey API").version("0.0.9").
	// .build();
	// }
}