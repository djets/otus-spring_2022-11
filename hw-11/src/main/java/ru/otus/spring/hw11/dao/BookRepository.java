package ru.otus.spring.hw11.dao;

import ru.otus.spring.hw11.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);

    Optional<Book> findByIdWithGenreAndAuthors(Long id);

    List<Book> findAll();

    List<Book> findByName(String name);

    void update(Book book);

    void delete(Book book);

    Optional<Book> getBookById(Long id);
}
