package ru.otus.spring.hw15.services;

import ru.otus.spring.hw15.model.Author;

import java.util.List;

public interface AuthorService {
    Long save(String name, String surname);

    Author findById(Long id, boolean loadBooks);

    List<Long> findByNameAndSurname(String name, String surname);

    List<Author> findAll();

    void updateNameAuthor(Long id, String name);

    void updateSurnameAuthor(Long id, String surname);

    void delete(Long id);
}
