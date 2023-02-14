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

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorServiceShellImpl implements AuthorServiceShell {
    AuthorRepository repository;

    @Override
    @Transactional
    public Long save(String name, String surname) {
        Optional<Author> optionalAuthor = repository.findByNameAndSurname(name, surname);
        if (optionalAuthor.isEmpty()) {
            Author author = new Author();
            author.setName(name);
            author.setSurname(surname);
            Author savedAuthor = repository.save(author);
            return savedAuthor.getId();
        }
        return optionalAuthor.get().getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Author findById(Long id) {
        Optional<Author> optionalAuthor = repository.findById(id);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        }
        System.out.println("Author id: " + id + " not found");
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findByNameAndSurname(String name, String surname) {
        Optional<Author> optionalAuthor = repository.findByNameAndSurname(name, surname);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get().getId();
        }
        System.out.println("Author name: " + name +
                ", surname: " + surname +
                " not found");
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void updateNameAuthor(Long id, String name) {
        repository.updateNameById(id, name);
    }

    @Override
    @Transactional
    public void updateSurnameAuthor(Long id, String surname) {
        repository.updateSurnameById(id, surname);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
