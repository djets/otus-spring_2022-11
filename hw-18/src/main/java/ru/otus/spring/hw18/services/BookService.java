package ru.otus.spring.hw18.services;

import ru.otus.spring.hw18.dto.BookDto;
import ru.otus.spring.hw18.dto.CommentDto;
import ru.otus.spring.hw18.model.Book;

import java.util.List;

public interface BookService {

    BookDto findById(String id);

    List<String> findByName(String name);

    List<BookDto> findAll();

    void delete(String _id);

    String save(BookDto bookDto);

    List<CommentDto> findCommentsByBookId(String bookId);
}
