package ru.otus.spring.hw09.dao;

import ru.otus.spring.hw09.model.Author;

import java.util.List;
import java.util.Map;

public interface AuthorDao {

    Long insert(Author author);
    int update(Long id, Map<String, String> changedParam);
    Author findById(Long id);
    List<Long> findByName (String name, String surname);
    List<Author> findAll();
    int delete(Long id);
}
