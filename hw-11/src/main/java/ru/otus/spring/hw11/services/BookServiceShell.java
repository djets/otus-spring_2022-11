package ru.otus.spring.hw11.services;

import ru.otus.spring.hw11.model.Book;

import java.util.List;

public interface BookServiceShell {

    Long save(String name);

    Long saveBookWithGenreAndAuthor(String name, String authorName, String authorSurname, String genreName);

    Book findById(Long id);

    Long findByName(String name);

    List<Book> findAll();

    void addAuthor(Long id, String authorName, String authorSurname);

    void addGenre(Long id, String nameGenre);

    void addCommentById(Long id, String commentText);

    void updateNameById(Long id, String changedName);

    void delete(Long id);
}
