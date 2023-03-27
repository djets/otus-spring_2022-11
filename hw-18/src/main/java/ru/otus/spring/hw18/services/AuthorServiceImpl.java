package ru.otus.spring.hw18.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw18.dto.AuthorDto;
import ru.otus.spring.hw18.dto.mapper.AuthorDtoMapper;
import ru.otus.spring.hw18.model.Author;
import ru.otus.spring.hw18.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    AuthorRepository repository;
    AuthorDtoMapper authorDtoMapper;

    @Override
    public void save(AuthorDto authorDto) {
        repository.save(authorDtoMapper.fromDto(authorDto));
    }

    @Override
    public Author findById(String _id) {
        return repository.findById(_id).orElse(null);
    }

    @Override
    public List<String> findByNameAndSurname(String name, String surname) {
        return repository.findByNameAndSurname(name, surname).stream()
                .map(Author::get_id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        repository.findAll().forEach(authors::add);
        return authors;
    }

    @Override
    public void updateNameAuthor(String _id, String changedName) {
        repository.findById(_id).ifPresent(author -> {
            author.setName(changedName);
            repository.save(author);
        });
    }

    @Override
    public void updateSurnameAuthor(String _id, String changedSurname) {
        repository.findById(_id).ifPresent(author -> {
            author.setSurname(changedSurname);
            repository.save(author);
        });
    }

    @Override
    public void delete(String _id) {
        repository.findById(_id).ifPresent(repository::delete);
    }
}
