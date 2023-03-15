package ru.otus.spring.hw15.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw15.repository.GenreRepository;
import ru.otus.spring.hw15.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    GenreRepository repository;

    @Override
    public String save(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        Genre savedGenre = repository.save(genre);
        return savedGenre.get_id();
    }

    @Override
    public Genre findById(String _id) {
        return repository.findById(_id).orElse(null);
    }

    @Override
    public List<String> findByName(String name) {
        return repository.findByName(name).stream()
                .map(Genre::get_id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        repository.findAll().forEach(genres::add);
        return genres;
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
