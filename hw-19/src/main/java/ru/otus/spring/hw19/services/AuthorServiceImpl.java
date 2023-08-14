package ru.otus.spring.hw19.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw19.controller.exception.NotFoundException;
import ru.otus.spring.hw19.dto.AuthorDto;
import ru.otus.spring.hw19.dto.mapper.AuthorDtoMapper;
import ru.otus.spring.hw19.model.Author;
import ru.otus.spring.hw19.repository.AuthorRepository;

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
    public AuthorDto findById(String _id) {
        return authorDtoMapper.toDto(repository.findById(_id).orElseThrow(NotFoundException::new));
    }

    @Override
    public List<String> findByNameAndSurname(String name, String surname) {
        List<Author> authorList = repository.findByNameAndSurname(name, surname);
        if (authorList.isEmpty()) {
            return null;
        }
        return authorList.stream()
                .map(Author::get_id)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> findAll() {
        List<Author> authorList = repository.findAll();
        if (authorList.isEmpty()) {
            return null;
        }
        return authorList.stream()
                .map(authorDtoMapper::toDto)
                .collect(Collectors.toList());
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
