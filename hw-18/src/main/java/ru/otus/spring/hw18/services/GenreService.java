package ru.otus.spring.hw18.services;

import ru.otus.spring.hw18.model.Genre;

import java.util.List;

public interface GenreService {
    String save(String name);

    Genre findById(String _id);

    List<String> findByName(String name);
    List<Genre> findAll();
    void updateNameById(String _id, String changedName);
    void delete(String _id);
}
