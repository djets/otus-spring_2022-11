package ru.otus.spring.hw18.services;

import ru.otus.spring.hw18.model.Book;

import java.util.List;

public interface BookService {

    String save(String name);

    String saveBookWithGenreAndAuthor(String name, String authorName, String authorSurname, String genreName);

    Book findById(String id);

    List<String> findByName(String name);

    List<Book> findAll();

    void addAuthor(String id, String authorName, String authorSurname);

    void addGenre(String _id, String nameGenre);

    void addCommentById(String _id, String commentText);

    void updateNameById(String _id, String changedName);

    void delete(String _id);
}
