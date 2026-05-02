package com.digitalmindkr.apirest.integrationtest.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.digitalmindkr.apirest.integrationtest.testcontainers.AbstractIntegrationTest;
import com.digitalmindkr.apirest.model.Person;
import com.digitalmindkr.apirest.repository.PersonRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)// CONFIGURA O BANCO REAL DA APLICAÇÃO NO TESTE E NÃO O H2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
class PersonRepositoryPersonTest extends AbstractIntegrationTest{
	
	@Autowired
	PersonRepository repository;
	private static Person person;

	@BeforeAll
	static void setUp() {
		person = new Person();
	}
	
	@Test
	@Order(1)
	void findPeopleByName() {
		PageRequest pageable = PageRequest.of(0, 12,Sort.by(Sort.Direction.ASC,"firstName"));
		person = repository.findPeopleByName("Lean", pageable).getContent().get(3);
		System.out.println(person.getFirstName() + " "+ person.getLastName() + " " + person.getAddress() + " " + person.getEnabled());
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Porto Alegre", person.getAddress());
        assertEquals("Leandro", person.getFirstName());
        assertEquals("Erudio", person.getLastName());
        assertEquals("Male", person.getGender());
        assertTrue(person.getEnabled());
	}
	
	@Test
    @Order(2)
    void disablePerson() {
		
        Long id = person.getId();
        repository.disablePerson(id);

        var result = repository.findById(id);
        person = result.get();

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Porto Alegre", person.getAddress());
        assertEquals("Leandro", person.getFirstName());
        assertEquals("Erudio", person.getLastName());
        assertEquals("Male", person.getGender());
        assertFalse(person.getEnabled());
    }
	
}
