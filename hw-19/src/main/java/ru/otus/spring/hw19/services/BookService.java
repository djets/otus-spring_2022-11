package ru.otus.spring.hw19.services;

import ru.otus.spring.hw19.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(String id);

    List<String> findByName(String name);

    List<BookDto> findAll();

    void delete(String _id);

    String save(BookDto bookDto);
}
