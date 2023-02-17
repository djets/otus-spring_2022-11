package ru.otus.spring.hw11.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.dao.AuthorRepository;
import ru.otus.spring.hw11.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceShellImpl implements AuthorServiceShell {
    AuthorRepository repository;

    @Override
    @Transactional
    public Long save(String name, String surname) {
        Author author = new Author();
        author.setName(name);
        author.setSurname(surname);
        Author savedAuthor = repository.save(author);
        return savedAuthor.getId();
    }

    @Override
    public Author findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Long> findByNameAndSurname(String name, String surname) {
        return repository.findByNameAndSurname(name, surname).stream()
                .map(Author::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void updateNameAuthor(Long id, String changedName) {
        repository.findById(id).ifPresent(author -> {
            author.setName(changedName);
            repository.update(author);
        });
    }

    @Override
    @Transactional
    public void updateSurnameAuthor(Long id, String changedSurname) {
        repository.findById(id).ifPresent(author -> {
            author.setSurname(changedSurname);
            repository.update(author);
        });
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
