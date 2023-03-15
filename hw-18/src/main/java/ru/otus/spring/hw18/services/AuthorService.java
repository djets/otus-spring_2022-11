package ru.otus.spring.hw18.services;

import ru.otus.spring.hw18.model.Author;

import java.util.List;

public interface AuthorService {
    String save(String name, String surname);

    Author findById(String _id);

    List<String> findByNameAndSurname(String name, String surname);

    List<Author> findAll();

    void updateNameAuthor(String _id, String name);

    void updateSurnameAuthor(String _id, String surname);

    void delete(String _id);
}
