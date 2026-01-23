package com.digitalmindkr.apirest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean //Objeto que é montado e gerenciado pelo spring-Boot
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("REST API´s RESTFfull from 0 with java, Spring-Boot ,Kubernets and Docker")
				.version("V1")
				.description("REST API´s RESTFfull from 0 with java, Spring-Boot ,Kubernets and Docker")
				.termsOfService("https://github.com/mvlrgtr")
				.license(new License()
						.name("Apache 2.0")
						.url("https://github.com/mvlrgtr")
						));
	}
}
