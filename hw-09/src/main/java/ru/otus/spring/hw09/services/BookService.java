package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Book;

public interface BookService {
    Book getById(Long id);
    void save(Book author);
    void delete(Long id);
}
