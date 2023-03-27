package ru.otus.spring.hw18.dto.mapper;

import org.springframework.stereotype.Component;
import ru.otus.spring.hw18.dto.GenreDto;
import ru.otus.spring.hw18.model.Genre;

import java.util.ArrayList;
import java.util.Optional;

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
