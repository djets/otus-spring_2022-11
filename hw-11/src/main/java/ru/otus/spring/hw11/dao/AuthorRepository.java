package ru.otus.spring.hw11.dao;

import ru.otus.spring.hw11.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);
    Optional<Author> findById(Long id);
    List<Author> findAll();

    Optional<Author> findByNameAndSurname(String name, String surname);

    void updateNameById(Long id,  String updatedName);
    void updateSurnameById(Long id,  String updatedSurname);

    void update(Author author);

    void deleteById(Long id);

    void delete(Author author);
}
