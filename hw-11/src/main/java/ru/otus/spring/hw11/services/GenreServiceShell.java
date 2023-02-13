package ru.otus.spring.hw11.services;

import ru.otus.spring.hw11.model.Genre;

import java.util.List;

public interface GenreServiceShell {
    Long save(String name);
    Genre findById(Long id);
    void updateNameById(Long id, String changedName);
    Long findByName(String name);
    void delete(Long id);
    List<Genre> getAll();
}
