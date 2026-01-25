package com.digitalmindkr.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.digitalmindkr.apirest.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
