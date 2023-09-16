package ru.otus.spring.hw24.services;

import ru.otus.spring.hw24.dto.AuthorDto;
import ru.otus.spring.hw24.model.Author;

import java.util.List;

public interface AuthorService {

    void save(AuthorDto authorDto);

    Author findById(String _id);

    List<String> findByNameAndSurname(String name, String surname);

    List<Author> findAll();

    void updateNameAuthor(String _id, String name);

    void updateSurnameAuthor(String _id, String surname);

    void delete(String _id);
}
