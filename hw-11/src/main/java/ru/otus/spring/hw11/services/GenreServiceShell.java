package ru.otus.spring.hw11.services;

import ru.otus.spring.hw11.model.Genre;

import java.util.List;

public interface GenreServiceShell {
    Long save(String name);
    Genre findById(Long id);
    Long findByName(String name);
    List<Genre> findAll();
    void updateNameById(Long id, String changedName);
    void delete(Long id);
}
