package ru.otus.spring.hw11.dao;

import ru.otus.spring.hw11.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);
    Optional<Author> findById(Long id);
    List<Author> findAll();

    List<Author> findByNameAndSurname(String name, String surname);

    void update(Author author);

    void delete(Author author);
}
