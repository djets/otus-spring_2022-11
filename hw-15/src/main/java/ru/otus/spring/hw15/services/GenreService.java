package ru.otus.spring.hw15.services;

import ru.otus.spring.hw15.model.Genre;

import java.util.List;

public interface GenreService {
    Long save(String name);
    Genre findById(Long id);
    List<Long> findByName(String name);
    List<Genre> findAll();
    void updateNameById(Long id, String changedName);
    void delete(Long id);
}
