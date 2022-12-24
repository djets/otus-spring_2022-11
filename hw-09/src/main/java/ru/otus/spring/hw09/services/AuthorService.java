package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Author;

public interface AuthorService {
    Author getById(Long id);
    void save(Author author);
    void delete(Long id);
}
