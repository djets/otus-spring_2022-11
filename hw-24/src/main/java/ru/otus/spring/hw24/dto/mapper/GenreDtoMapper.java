package ru.otus.spring.hw24.dto.mapper;

import org.springframework.stereotype.Component;
import ru.otus.spring.hw24.dto.GenreDto;
import ru.otus.spring.hw24.model.Genre;

import java.util.ArrayList;

@Component
public class GenreDtoMapper implements DtoMapper<Genre, GenreDto> {
    @Override
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.get_id(), genre.getName());
    }

    @Override
    public Genre fromDto(GenreDto genreDto) {
        return new Genre(
                genreDto.getId(),
                genreDto.getNameGenre(),
                new ArrayList<>()
        );
    }
}
