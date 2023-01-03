package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;

import java.util.List;

public interface BookService {
    void save(Book book);
    void save(Long id, Book book);
    Long saveIfAbsentName(String name);
    Book getById(Long id);
    void delete(Long id);
    List<Book> getAll();
}
