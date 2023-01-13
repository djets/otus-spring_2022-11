package ru.otus.spring.hw09.dao;

import ru.otus.spring.hw09.model.Book;

import java.util.List;
import java.util.Map;

public interface BookDao {
    Long insert(Book book);
    int update(Long id, Map<String, Object> changedParam);
    Book findById(Long id);
    List<Long> findByName (String name);
    List<Book> findAll();
    int delete(Long id);
}
