package ru.otus.spring.hw19.services;

import ru.otus.spring.hw19.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    void save(AuthorDto authorDto);

    AuthorDto findById(String _id);

    List<String> findByNameAndSurname(String name, String surname);

    List<AuthorDto> findAll();

    void updateNameAuthor(String _id, String name);

    void updateSurnameAuthor(String _id, String surname);

    void delete(String _id);
}
