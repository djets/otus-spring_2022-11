package ru.otus.spring.hw24.dto.mapper;

import org.springframework.stereotype.Component;
import ru.otus.spring.hw24.dto.AuthorDto;
import ru.otus.spring.hw24.model.Author;

import java.util.ArrayList;

@Component
public class AuthorDtoMapper implements DtoMapper<Author, AuthorDto> {
    @Override
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.get_id(), author.getName(), author.getSurname());
    }

    @Override
    public Author fromDto(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName(), authorDto.getSurname(), new ArrayList<>());
    }
}
