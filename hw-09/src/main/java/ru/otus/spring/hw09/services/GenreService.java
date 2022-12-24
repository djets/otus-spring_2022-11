package ru.otus.spring.hw09.services;

import ru.otus.spring.hw09.model.Genre;

public interface GenreService {
    Genre getById(Long id);
    void save(Genre author);
    void delete(Long id);
}
