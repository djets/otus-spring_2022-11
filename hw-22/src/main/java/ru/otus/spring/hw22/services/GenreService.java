package ru.otus.spring.hw19.services;

import ru.otus.spring.hw19.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto save(GenreDto genreDto);

    GenreDto findById(String _id);

    List<GenreDto> findByName(String name);
    List<GenreDto> findAll();
    void updateNameById(String _id, String changedName);
    void delete(String _id);
}
