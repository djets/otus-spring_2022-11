package ru.otus.spring.hw09.dao;

import ru.otus.spring.hw09.model.Genre;

import java.util.List;

public interface GenreDao {
    Long insert(Genre genre);
    int update(Long id, String changedName);
    Genre findById(Long id);
    List<Long> findByName (String name);
    List<Genre> findAll();
    int delete(Long id);
}
