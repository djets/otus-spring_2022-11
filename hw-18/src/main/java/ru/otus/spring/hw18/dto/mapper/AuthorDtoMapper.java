package ru.otus.spring.hw18.dto.mapper;

import org.springframework.stereotype.Component;
import ru.otus.spring.hw18.dto.AuthorDto;
import ru.otus.spring.hw18.model.Author;

import java.util.ArrayList;

@Component
public class AuthorDtoMapper implements DtoMapper<Author, AuthorDto> {
    @Override
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getName(), author.getSurname());
    }

    @Override
    public Author fromDto(AuthorDto authorDto) {
        return new Author(null, authorDto.getName(), authorDto.getSurname(), new ArrayList<>());
    }
}
