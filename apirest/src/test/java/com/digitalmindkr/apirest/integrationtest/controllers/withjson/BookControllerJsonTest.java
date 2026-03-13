package com.digitalmindkr.apirest.integrationtest.controllers.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import com.digitalmindkr.apirest.integrationtest.dto.BookDTO;
import com.digitalmindkr.apirest.integrationtest.dto.wrappers.json.WrapperBookDTO;
import com.digitalmindkr.apirest.integrationtest.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // isso serve para O Junit seguir uma ordenação nos testes , caso
														// o proximo dependa do anterior
class BookControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static BookDTO book;
	Date expectedDate = Date.from(
	        LocalDate.of(2026, 2, 5)
	                .atStartOfDay(ZoneId.systemDefault())
	                .toInstant()
	);


	@BeforeAll
	void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		book = new BookDTO();
	}

	@Test
	@Order(1)
	void createTest() throws JsonProcessingException {
		mockbook();

		specification = new RequestSpecBuilder() // montando a requisição para ser enviada
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_DIGITALMINDKR).setBasePath("/book")
				.setPort(TestConfigs.SERVER_PORT).addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL)).build();

		var content = given(specification) // aqui de fato a requisição e feita no nosso teste de integração para depois
											// compararmos as respostas
				.contentType(MediaType.APPLICATION_JSON_VALUE).body(book).when().post().then().statusCode(200)
				.extract().body().asString(); // o problema aqui que contente e uma string e por isso precisamos
												// converter ela em um objeto para poder fazer a manipulação

		BookDTO createdbook = objectMapper.readValue(content, BookDTO.class);
		book = createdbook;

		assertNotNull(createdbook.getId());
		assertTrue(createdbook.getId() > 0);

		assertEquals("Angelo Reboucas", createdbook.getAuthor());
		assertEquals(createdbook.getDate(), expectedDate);
		assertEquals(createdbook.getDate(), new BigDecimal("104.4"));
		assertEquals(createdbook.getTitle(), "Como conquistar uma mulher perigosa");
		
	}

	@Test
	@Order(2)
	void updateTest() throws JsonProcessingException {
		book.setTitle("Como conquistar uma mulher perigosa 2");

		var content = given(specification)
	            .contentType(MediaType.APPLICATION_JSON_VALUE)
	                .body(book)
	            .when()
	                .put()
	            .then()
	                .statusCode(200)
	                .contentType(MediaType.APPLICATION_JSON_VALUE)
	            .extract()
	                .body()
	                    .asString();

		BookDTO createdbook = objectMapper.readValue(content, BookDTO.class);
		book = createdbook;

		assertNotNull(createdbook.getId());
		assertTrue(createdbook.getId() > 0);

		assertEquals("Angelo Reboucas", createdbook.getAuthor());
		assertEquals(createdbook.getDate(), expectedDate);
		assertEquals(createdbook.getDate(), new BigDecimal("104.4"));
		assertEquals(createdbook.getTitle(), "Como conquistar uma mulher perigosa 2");

	}

	@Test
	@Order(3)
	void findByIdTest() throws JsonProcessingException {

		var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", book.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
                .body()
                    .asString();

		BookDTO createdbook = objectMapper.readValue(content, BookDTO.class);
		book = createdbook;

		assertNotNull(createdbook.getId());
		assertTrue(createdbook.getId() > 0);

		assertEquals("Angelo Reboucas", createdbook.getAuthor());
		assertEquals(createdbook.getDate(), expectedDate);
		assertEquals(createdbook.getDate(), new BigDecimal("104.4"));
		assertEquals(createdbook.getTitle(), "Como conquistar uma mulher perigosa 2");
	}


	@Test
	@Order(4)
	void deleteTest() throws JsonProcessingException {

		given(specification).pathParam("id", book.getId()).when().delete("{id}").then().statusCode(204);
	}

	@Test
	@Order(5)
	void findAllTest() throws JsonProcessingException {

		var content = given(specification).accept(MediaType.APPLICATION_JSON_VALUE).queryParam("page", 1,"size" , 12 ,"direction","asc").when().get().then().statusCode(200)
				.contentType(MediaType.APPLICATION_JSON_VALUE).extract().body().asString();

		
		WrapperBookDTO wrapper = objectMapper.readValue(content, WrapperBookDTO.class);
		List<BookDTO> books = wrapper.getEmbedded().getbooks();

		BookDTO bookOne = books.get(0);

		assertNotNull(bookOne.getId());
		assertTrue(bookOne.getId() > 0);

		assertEquals("Angelo Reboucas",bookOne.getAuthor());
		assertEquals(bookOne.getDate(), expectedDate);
		assertEquals(bookOne.getDate(), new BigDecimal("104.4"));
		assertEquals(bookOne.getTitle(), "Como conquistar uma mulher perigosa 2");

		BookDTO bookFour = books.get(3);

		assertNotNull(bookFour.getId());
		assertTrue(bookFour.getId() > 0);

		assertEquals("Angelo Reboucas", bookFour.getAuthor());
		assertEquals(bookFour.getDate(), expectedDate);
		assertEquals(bookFour.getDate(), new BigDecimal("104.4"));
		assertEquals(bookFour.getTitle(), "Como conquistar uma mulher perigosa 2");
	}
	
	
	private void mockbook() {
		Date date = Date.from(
		        LocalDate.of(2026, 2, 5)
		        .atStartOfDay(ZoneId.systemDefault())
		        .toInstant()
		);

		book.setAuthor("Angelo Reboucas");
		book.setDate(date);
		book.setId(1L);
		book.setPrice(new BigDecimal("104.4"));
		book.setTitle("Como conquistar uma mulher perigosa");
	}

}
