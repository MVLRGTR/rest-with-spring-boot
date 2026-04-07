package com.digitalmindkr.apirest.integrationtest.controllers.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
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
import com.digitalmindkr.apirest.integrationtest.dto.wrappers.PagedModelBook;
import com.digitalmindkr.apirest.integrationtest.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerYamlTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static YAMLMapper objectMapper;
	private static BookDTO book;
	Date expectedDate = Date.from(
	        LocalDate.of(2026, 2, 5)
	                .atStartOfDay(ZoneId.systemDefault())
	                .toInstant()
	);

	@BeforeAll
	void setUp() {
		objectMapper = new YAMLMapper();
		book = new BookDTO();
	}

	@Test
	@Order(1)
	void createTest() throws JsonProcessingException {
		mockbook();

		specification = new RequestSpecBuilder() 
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_DIGITALMINDKR).setBasePath("/book")
				.setPort(TestConfigs.SERVER_PORT).addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL)).build();

		var createdbook = (BookDTO) given().config(
                RestAssuredConfig.config()
                .encoderConfig(
                    EncoderConfig.encoderConfig().
                        encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
            ).spec(specification)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .accept(MediaType.APPLICATION_YAML_VALUE)
            .body(book, objectMapper)
        .when()
            .post()
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_YAML_VALUE)
        .extract()
            .body()
                .as(BookDTO.class, objectMapper);
					

		
		book = createdbook;

		assertNotNull(createdbook.getId());
		assertTrue(createdbook.getId() > 0);

		assertEquals("Angelo Reboucas", createdbook.getAuthor());
		assertEquals(createdbook.getDate(), expectedDate);
		assertEquals(createdbook.getPrice(), new BigDecimal("104.4"));
		assertEquals(createdbook.getTitle(), "Como conquistar uma mulher perigosa");
	}

	@Test
	@Order(2)
	void updateTest() throws JsonProcessingException {
		book.setTitle("Como conquistar uma mulher perigosa 2");

		var createdbook =(BookDTO) given().config(
                RestAssuredConfig.config()
                        .encoderConfig(
                                EncoderConfig.encoderConfig().
                                        encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))).spec(specification)
		    .contentType(MediaType.APPLICATION_YAML_VALUE)
		    .accept(MediaType.APPLICATION_YAML_VALUE)
		        .body(book, objectMapper)
		    .when()
		        .put()
		    .then()
		        .statusCode(200)
		        .contentType(MediaType.APPLICATION_YAML_VALUE)
		    .extract()
		        .body()
		        .as(BookDTO.class, objectMapper);

		
		book = createdbook;

		assertNotNull(createdbook.getId());
		assertTrue(createdbook.getId() > 0);

		assertEquals("Angelo Reboucas", createdbook.getAuthor());
		assertEquals(createdbook.getDate(), expectedDate);
		assertEquals(createdbook.getPrice(), new BigDecimal("104.4"));
		assertEquals(createdbook.getTitle(), "Como conquistar uma mulher perigosa 2");

	}

	@Test
	@Order(3)
	void findByIdTest() throws JsonProcessingException {

		var createdbook = (BookDTO) given().config(
                RestAssuredConfig.config()
                        .encoderConfig(
                                EncoderConfig.encoderConfig().
                                        encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
        ).spec(specification)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .accept(MediaType.APPLICATION_YAML_VALUE)
            .pathParam("id", book.getId())
        .when()
            .get("{id}")
        .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_YAML_VALUE)
        .extract()
            .body()
        .as(BookDTO.class, objectMapper);
		
		book = createdbook;

		assertNotNull(createdbook.getId());
		assertTrue(createdbook.getId() > 0);

		assertEquals("Angelo Reboucas", createdbook.getAuthor());
		assertEquals(createdbook.getDate(), expectedDate);
		assertEquals(createdbook.getPrice(), new BigDecimal("104.40"));
		assertEquals(createdbook.getTitle(), "Como conquistar uma mulher perigosa 2");
	}

	

	@Test
	@Order(4)
	void deleteTest() throws JsonProcessingException {

		given(specification)
	        .pathParam("id", book.getId())
	    .when()
	        .delete("{id}")
	    .then()
	        .statusCode(204);
	}

	@Test
	@Order(5)
	void findAllTest() throws JsonProcessingException {

		var response = given(specification).accept(MediaType.APPLICATION_YAML_VALUE).queryParam("page", 1,"size" , 12 ,"direction","asc").when().get().then().statusCode(200)
				.contentType(MediaType.APPLICATION_YAML_VALUE).extract().body().as(PagedModelBook.class,objectMapper);

		List<BookDTO> books = response.getContent();

		BookDTO bookOne = books.get(0);

		assertNotNull(bookOne.getId());
		assertTrue(bookOne.getId() > 0);
		
		Date date = Date.from(
		        LocalDate.of(2017, 11, 7)
		        .atStartOfDay(ZoneId.systemDefault())
		        .toInstant()
		);

		assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier",bookOne.getAuthor());
		assertEquals(bookOne.getDate(), date);
		assertTrue(bookOne.getPrice().compareTo(new BigDecimal("54.0")) == 0);
		assertEquals(bookOne.getTitle(), "Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana");

		BookDTO bookFour = books.get(3);

		assertNotNull(bookFour.getId());
		assertTrue(bookFour.getId() > 0);
		
		date = Date.from(
		        LocalDate.of(2017, 11,29)
		        .atStartOfDay(ZoneId.systemDefault())
		        .toInstant()
		);

		assertEquals("Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm", bookFour.getAuthor());
		assertEquals(bookFour.getDate(),date);
		assertTrue(bookFour.getPrice().compareTo(new BigDecimal("45.0")) == 0);
		assertEquals(bookFour.getTitle(), "Design Patterns");
	}
	
	
	private void mockbook() {
		Date date = Date.from(
		        LocalDate.of(2026, 2, 5)
		        .atStartOfDay(ZoneId.systemDefault())
		        .toInstant()
		);

		book.setAuthor("Angelo Reboucas");
		book.setDate(date);
		book.setPrice(new BigDecimal("104.4"));
		book.setTitle("Como conquistar uma mulher perigosa");
	}

}
