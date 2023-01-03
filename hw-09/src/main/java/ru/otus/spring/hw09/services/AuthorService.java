package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Author;

import java.util.List;

public interface AuthorService {
    void save(Author author);
    void save(Long id, Author author);
    Long saveIfAbsentFirstNameLastName(String authorFirstName, String authorLastName);
    Author getById(Long id);
    void delete(Long id);
    List<Author> getAll();
}
