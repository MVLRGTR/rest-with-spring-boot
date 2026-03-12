package com.digitalmindkr.apirest.unitest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;

import com.digitalmindkr.apirest.data.dto.v1.PersonDTO;
import com.digitalmindkr.apirest.exception.RequiredObjectIsNullException;
import com.digitalmindkr.apirest.model.Person;
import com.digitalmindkr.apirest.repository.PersonRepository;
import com.digitalmindkr.apirest.services.PersonService;
import com.digitalmindkr.apirest.unitest.mapper.mocks.MockPerson;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // aqui eu faço a definição do tempo de vida dos objetos aqui criados somente para essa classe
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	MockPerson input;
	
	@InjectMocks // nessa parte eu crio o objeto real que fará uso do @Mock
	private PersonService service;
	
	@Mock // aqui eu crio um objeto "falso" com uma casca vazia da classe PersonRepository , por padrão esse objeto não faz nada e retorna null se chamado pois queremos controle total sobre o que ele retorna
	PersonRepository repository;
	
	@Mock // Adicione isso para evitar NullPointerException!
	PagedResourcesAssembler<PersonDTO> assembler;

	@BeforeEach
	void setUp() {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void FindById() {
		Person person = input.mockEntity(1);
		person.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(person)); //Quando o repository for chamado passo o objeto que tem que ser retornado com controle
		
		var result = service.findById(1L);
		
		assertNotNull(result);                //informa aqui que não pode ser nulo o retorno
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("/person/1")
						&& link.getType().equals("GET")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("GET")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("POST")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("PUT")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("DELETE")
						)
				);
		
		assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
	}

	
	@Test
	void create() {
		Person person = input.mockEntity(1);
		Person persisted = person;
		person.setId(1L);
		
		PersonDTO dto = input.mockDTO(1);
		
		when(repository.save(person)).thenReturn(persisted);
		
		var result = service.create(dto);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("/person/1")
						&& link.getType().equals("GET")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("GET")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("POST")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("PUT")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("DELETE")
						)
				);
		
		assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
		
	}
	
	@Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
        () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
	
	@Test
	void update() {
		Person person = input.mockEntity(1);
		Person persisted = person;
		person.setId(1L);
		
		PersonDTO dto = input.mockDTO(1);
		
		when(repository.findById(1L)).thenReturn(Optional.of(person));
		when(repository.save(person)).thenReturn(persisted);
		
		var result = service.update(dto);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("/person/1")
						&& link.getType().equals("GET")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("GET")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("POST")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("PUT")
						)
				);
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("/person/")
						&& link.getType().equals("DELETE")
						)
				);
		
		assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
		
	}
	
	@Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
	
	@Test
	void delete() {
		Person person = input.mockEntity(1);
		person.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(person));
		
		service.delete(1L);
		verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
	}
	
	@Test 
	void findAll() {
		
		List<Person> list = input.mockEntityList();
        org.springframework.data.domain.Page<Person> page = new org.springframework.data.domain.PageImpl<>(list);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        
        // CORREÇÃO: Ensinando o mock do assembler a pegar a página gerada pelo serviço e empacotar em um PagedModel
        when(assembler.toModel(any(org.springframework.data.domain.Page.class), any(org.springframework.hateoas.Link.class)))
            .thenAnswer(invocation -> {
                org.springframework.data.domain.Page<PersonDTO> pageArg = invocation.getArgument(0);
                List<EntityModel<PersonDTO>> entityModels = pageArg.getContent().stream()
                        .map(EntityModel::of)
                        .toList();
                return PagedModel.of(entityModels, new PagedModel.PageMetadata(pageArg.getSize(), pageArg.getNumber(), pageArg.getTotalElements()));
            });
        
        PageRequest pageable = PageRequest.of(0, 12, Sort.by("asc","firstName"));
        
        PagedModel<EntityModel<PersonDTO>> pagedModel = service.findAll(pageable);
        
        List<EntityModel<PersonDTO>> people = pagedModel.getContent().stream().toList();
        
        assertNotNull(people);
		assertNotNull(people);
		assertEquals(14,people.size());
		
		var personOne = people.get(1).getContent();
		
		assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getLinks());

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personFour = people.get(4).getContent();

        assertNotNull(personFour);
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getLinks());

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person/4")
                        && link.getType().equals("GET")
                ));

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/person/4")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        var personSeven = people.get(7).getContent();

        assertNotNull(personSeven);
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getLinks());

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person7")
                        && link.getType().equals("GET")
                ));

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/person/")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/person/7")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());
	}
	
}
