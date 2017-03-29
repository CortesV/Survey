package com.softbistro.survey.startapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softbistro.survey.response.Response;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@org.springframework.context.annotation.Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build()
				.globalResponseMessage(RequestMethod.GET, getDefaultResponseMessages()).apiInfo(metadata());
	}

	private List<ResponseMessage> getDefaultResponseMessages() {
		List<ResponseMessage> defaultResponseMessages = new ArrayList<ResponseMessage>();
		defaultResponseMessages.add(new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
				(ModelReference) new Response(new Object(), HttpStatus.OK, ""), null, null));
		defaultResponseMessages.add(new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),	(ModelReference) new Response(new Object(), 
						HttpStatus.INTERNAL_SERVER_ERROR, ""), null, null));
		return defaultResponseMessages;
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder().title("Survey API").description("Documentation for Survey API").version("0.0.4")
				.build();
	}
}