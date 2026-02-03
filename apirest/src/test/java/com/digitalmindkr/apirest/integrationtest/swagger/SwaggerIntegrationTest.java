package com.digitalmindkr.apirest.integrationtest.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.digitalmindkr.apirest.config.TestConfigs;
import com.digitalmindkr.apirest.integrationtest.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest{
	
	@Test
	void shouldDisplaySwaggerUIPage() {
		var content = given()
			.basePath("/swagger-ui/index.html")
				.port(TestConfigs.SERVER_PORT)
			.when()
				.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		assertTrue(content.contains("Swagger UI"));
	}

}
