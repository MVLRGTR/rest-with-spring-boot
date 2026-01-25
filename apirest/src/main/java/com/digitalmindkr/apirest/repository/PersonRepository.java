package com.digitalmindkr.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.digitalmindkr.apirest.model.Person;

//No T eu passo a entidade e no Id eu passo o tipo que no caso de Person é Long
public interface PersonRepository extends JpaRepository<Person, Long>{
}
