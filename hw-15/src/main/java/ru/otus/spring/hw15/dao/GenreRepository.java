package ru.otus.spring.hw15.dao;

import ru.otus.spring.hw15.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);
    Optional<Genre> findById(Long id);
    List<Genre> findAll();

    List<Genre> findByName(String name);

    void update(Genre genre);

    void delete(Genre genre);
}
