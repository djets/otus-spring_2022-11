package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Author;

import java.util.List;

public interface AuthorService {
    Author getById(Long id);

    Long save(String name, String surname);

    void update(Long id, String name, String surname);

    Long findByFirstNameLastName(String name, String surname);

    boolean delete(Long id);

    List<Author> getAll();
}
