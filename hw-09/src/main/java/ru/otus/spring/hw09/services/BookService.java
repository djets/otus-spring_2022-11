package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Book;

import java.util.List;

public interface BookService {
    Book getById(Long id);

    Long save(String name, String authorName, String authorSurname, String genreName);

    void update(Long id, String authorNameSurname, String genreName);

    Long findByName(String name);

    boolean delete(Long id);

    List<Book> getAll();
}
