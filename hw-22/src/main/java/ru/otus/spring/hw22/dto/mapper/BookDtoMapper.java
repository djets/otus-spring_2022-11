package ru.otus.spring.hw22.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw22.dto.BookDto;
import ru.otus.spring.hw22.model.Author;
import ru.otus.spring.hw22.model.Book;
import ru.otus.spring.hw22.model.Genre;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookDtoMapper implements DtoMapper<Book, BookDto> {

    @Override
    public BookDto toDto(Book book) {
        return new BookDto(
                book.get_id(),
                book.getTitle(),
                Optional.of(book.getGenre()).orElse(null).getName(),
                Optional.of(book.getAuthors()).orElse(null).stream()
                        .filter(Objects::nonNull)
                        .map(author -> author.getName() + " " + author.getSurname())
                        .collect(Collectors.toList()));
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        if (bookDto.id() != null) {
            return new Book(
                    bookDto.id(),
                    bookDto.title(),
                    bookDto.authorList().stream()
                            .map(s -> new Author(null, s.split(" ")[0], s.split(" ")[1]))
                            .collect(Collectors.toList()),
                    new Genre(null, bookDto.genreName())
            );
        }
        return new Book(
                null,
                bookDto.title(),
                bookDto.authorList().stream()
                        .map(s -> new Author(null, s.split(" ")[0], s.split(" ")[1]))
                        .collect(Collectors.toList()),
                new Genre(null, bookDto.genreName())
        );
    }
}
