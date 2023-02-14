package ru.otus.spring.hw11.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.dao.GenreRepository;
import ru.otus.spring.hw11.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreServiceShellImpl implements GenreServiceShell {
    GenreRepository repository;

    @Override
    @Transactional
    public Long save(String name) {
        Optional<Genre> optionalGenre = repository.findByName(name);
        if (optionalGenre.isEmpty()) {
            Genre genre = new Genre();
            genre.setName(name);
            Genre savedGenre = repository.save(genre);
            return savedGenre.getId();
        }
        return optionalGenre.get().getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        Optional<Genre> optionalGenre = repository.findById(id);
        if (optionalGenre.isPresent()) {
            return optionalGenre.get();
        }
        System.out.println("Genre id: " + id + " not found");
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findByName(String name) {
        Optional<Genre> optionalGenre = repository.findByName(name);
        if (optionalGenre.isPresent()) {
            return optionalGenre.get().getId();
        }
        System.out.println("Genre name: " + name + " not found");
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String changedName) {
        repository.updateNameById(id, changedName);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
