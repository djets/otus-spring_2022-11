package ru.otus.spring.hw11.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.dao.GenreRepository;
import ru.otus.spring.hw11.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    GenreRepository repository;

    @Override
    @Transactional
    public Long save(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        Genre savedGenre = repository.save(genre);
        return savedGenre.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findByName(String name) {
        return repository.findByName(name).stream()
                .map(Genre::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    public void updateNameById(Long id, String changedName) {
        repository.findById(id).ifPresent(genre -> {
            genre.setName(changedName);
            repository.save(genre);
        });
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
