package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Genre;

import java.util.List;

public interface GenreService {
    Genre getById(Long id);
    void save(Genre genre);
    void save(Long id, Genre genre);
    Long saveIfAbsentName(String name);
    void delete(Long id);
    List<Genre> getAll();
}
