package com.digitalmindkr.apirest.integrationtest.controllers.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.digitalmindkr.apirest.config.TestConfigs;
import com.digitalmindkr.apirest.integrationtest.dto.PersonDTO;
import com.digitalmindkr.apirest.integrationtest.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

//o metodo para XML é igual ao do JSOn com a mudança de que para XML mudamos o ObjectMapper que agora é um XML mapper bem como a requisição para operar em XML
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerXMLTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static XmlMapper objectMapper;
	private static PersonDTO person;

	@BeforeAll
	void setUp() {
		objectMapper = new XmlMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		person = new PersonDTO();
	}

	@Test
	@Order(1)
	void createTest() throws JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder() 
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_DIGITALMINDKR).setBasePath("/person")
				.setPort(TestConfigs.SERVER_PORT).addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL)).build();

		var content = given(specification) 				
				.contentType(MediaType.APPLICATION_XML_VALUE)
				.accept(MediaType.APPLICATION_XML_VALUE)
				.body(person).when().post().then().statusCode(200)
				.extract().body().asString(); 
					

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;

		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);

		assertEquals("Linus", createdPerson.getFirstName());
		assertEquals("Torvalds", createdPerson.getLastName());
		assertEquals("Helsinki - Finland", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());
	}

	@Test
	@Order(2)
	void updateTest() throws JsonProcessingException {
		person.setLastName("Benedict Torvalds");

		var content = given(specification)
	            .contentType(MediaType.APPLICATION_XML_VALUE)
	            .accept(MediaType.APPLICATION_XML_VALUE) //isso informa a API que eu quero receber XML de volta
	                .body(person)
	            .when()
	                .put()
	            .then()
	                .statusCode(200)
	                .contentType(MediaType.APPLICATION_XML_VALUE)
	            .extract()
	                .body()
	                    .asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;

		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);

		assertEquals("Linus", createdPerson.getFirstName());
		assertEquals("Benedict Torvalds", createdPerson.getLastName());
		assertEquals("Helsinki - Finland", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());

	}

	@Test
	@Order(3)
	void findByIdTest() throws JsonProcessingException {

		var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", person.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
            .extract()
                .body()
                    .asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;

		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);

		assertEquals("Linus", createdPerson.getFirstName());
		assertEquals("Benedict Torvalds", createdPerson.getLastName());
		assertEquals("Helsinki - Finland", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());
	}

	@Test
	@Order(4)
	void disableTest() throws JsonProcessingException {

		var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", person.getId())
            .when()
                .patch("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
            .extract()
                .body()
                    .asString();
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;

		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);

		assertEquals("Linus", createdPerson.getFirstName());
		assertEquals("Benedict Torvalds", createdPerson.getLastName());
		assertEquals("Helsinki - Finland", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertFalse(createdPerson.getEnabled());
	}

	@Test
	@Order(5)
	void deleteTest() throws JsonProcessingException {

		given(specification).accept(MediaType.APPLICATION_XML_VALUE).pathParam("id", person.getId()).when().delete("{id}").then().statusCode(204);
	}

	@Test
	@Order(6)
	void findAllTest() throws JsonProcessingException {

		var content = given(specification).accept(MediaType.APPLICATION_XML_VALUE).when().get().then().statusCode(200)
				.contentType(MediaType.APPLICATION_XML_VALUE).extract().body().asString();

		List<PersonDTO> people = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {
		});

		PersonDTO personOne = people.get(0);

		assertNotNull(personOne.getId());
		assertTrue(personOne.getId() > 0);

		assertEquals("Leandro", personOne.getFirstName());
		assertEquals("Carnal Mafra Mafra", personOne.getLastName());
		assertEquals("Uberlândia - Minas Gerais - Brasil", personOne.getAddress());
		assertEquals("Male", personOne.getGender());
		assertTrue(personOne.getEnabled());

		PersonDTO personFour = people.get(3);

		assertNotNull(personFour.getId());
		assertTrue(personFour.getId() > 0);

		assertEquals("Leandro", personFour.getFirstName());
		assertEquals("Erudio", personFour.getLastName());
		assertEquals("Porto Alegre", personFour.getAddress());
		assertEquals("Male", personFour.getGender());
		assertTrue(personFour.getEnabled());
	}
	
	
	private void mockPerson() {
		person.setFirstName("Linus");
		person.setLastName("Torvalds");
		person.setAddress("Helsinki - Finland");
		person.setGender("Male");
		person.setEnabled(true);
	}

}
