package ru.otus.spring.hw19.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw19.controller.exception.NotFoundException;
import ru.otus.spring.hw19.dto.GenreDto;
import ru.otus.spring.hw19.dto.mapper.DtoMapper;
import ru.otus.spring.hw19.dto.mapper.GenreDtoMapper;
import ru.otus.spring.hw19.model.Genre;
import ru.otus.spring.hw19.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    GenreRepository repository;
    DtoMapper<Genre, GenreDto> genreDtoMapper;

    @Override
    public GenreDto save(GenreDto genreDto) {
        return genreDtoMapper.toDto(repository.save(genreDtoMapper.fromDto(genreDto)));
    }

    @Override
    public GenreDto findById(String _id) {
        return genreDtoMapper.toDto(repository.findById(_id).orElseThrow(NotFoundException::new));
    }

    @Override
    public List<GenreDto> findByName(String name) {
        List<Genre> genreList = repository.findByName(name);
        if (genreList.isEmpty()) {
            return null;
        }
        return genreList.stream()
                .map(genreDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreDto> findAll() {
        List<Genre> genreList = repository.findAll();
        if (genreList.isEmpty()) {
            return null;
        }
        return genreList.stream()
                .map(genreDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateNameById(String _id, String changedName) {
        repository.findById(_id).ifPresent(genre -> {
            genre.setName(changedName);
            repository.save(genre);
        });
    }

    @Override
    public void delete(String _id) {
        repository.findById(_id).ifPresent(repository::delete);
    }
}
