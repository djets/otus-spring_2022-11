package ru.otus.spring.hw18.services;

import ru.otus.spring.hw18.dto.CommentDto;
import ru.otus.spring.hw18.model.Book;

import java.util.List;

public interface BookService {

    Book findById(String id);

    List<String> findByName(String name);

    List<Book> findAll();

    void addAuthor(String id, String authorName, String authorSurname);

    void addGenre(String _id, String nameGenre);

    void addCommentById(String _id, String commentText);

    void updateNameById(String _id, String changedName);

    void delete(String _id);

    String save(Book book);

    List<CommentDto> findCommentsByBookId(String bookId);
}
