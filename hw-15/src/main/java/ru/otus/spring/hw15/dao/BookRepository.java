package ru.otus.spring.hw15.dao;

import ru.otus.spring.hw15.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);

    List<Book> findAll();

    List<Book> findByName(String name);

    void update(Book book);

    void delete(Book book);
}
