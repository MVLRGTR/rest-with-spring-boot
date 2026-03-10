package com.digitalmindkr.apirest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalmindkr.apirest.model.Person;

//No T eu passo a entidade e no Id eu passo o tipo que no caso de Person é Long
public interface PersonRepository extends JpaRepository<Person, Long>{
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Person as p SET p.enabled = false WHERE p.id = :id")
	void disablePerson(@Param("id") Long id);
	
	
	@Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT ('%',:firstName,'%'))")
	Page<Person> findPeopleByName(@Param("firstName") String firstName, Pageable pageable);
}
