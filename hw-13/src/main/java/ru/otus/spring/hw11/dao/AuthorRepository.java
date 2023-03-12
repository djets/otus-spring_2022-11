package ru.otus.spring.hw11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw11.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameAndSurname(String name, String surname);
}
