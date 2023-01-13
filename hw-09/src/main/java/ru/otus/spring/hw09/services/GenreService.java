package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Genre;

import java.util.List;

public interface GenreService {
    Genre getById(Long id);
    Long save(String name);
    void update(Long id, String changedName);
    Long findByName(String name);
    boolean delete(Long id);
    List<Genre> getAll();
}
