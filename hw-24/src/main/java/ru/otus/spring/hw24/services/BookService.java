package ru.otus.spring.hw24.services;

import ru.otus.spring.hw24.dto.BookDto;
import ru.otus.spring.hw24.dto.CommentDto;

import java.util.List;

public interface BookService {

    BookDto findById(String id);

    List<String> findByName(String name);

    List<BookDto> findAll();

    void delete(String _id);

    String save(BookDto bookDto);

    List<CommentDto> findCommentsByBookId(String bookId);
}
