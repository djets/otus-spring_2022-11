package ru.otus.spring.hw19.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw19.dto.BookDto;
import ru.otus.spring.hw19.model.Book;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookDtoMapper implements DtoMapper<Book, BookDto> {
    AuthorDtoMapper authorDtoMapper;
    GenreDtoMapper genreDtoMapper;

    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.get_id());
        bookDto.setTitle(book.getTitle());
        Optional.ofNullable(book.getGenre())
                .ifPresent(genre -> bookDto.setGenreDto(genreDtoMapper.toDto(genre)));
        Optional.ofNullable(book.getAuthors())
                .ifPresent(authors -> {
                            if (!book.getAuthors().isEmpty()) {
                                bookDto.setAuthorDtoList(
                                        authors.stream()
                                                .map(authorDtoMapper::toDto)
                                                .collect(Collectors.toList()));
                            }
                        }
                );
        return bookDto;
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        Book book = new Book();
        book.set_id(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        if (bookDto.getGenreDto() != null) {
            book.setGenre(genreDtoMapper.fromDto(bookDto.getGenreDto()));
        }
        if (bookDto.getAuthorDtoList() != null && !bookDto.getAuthorDtoList().isEmpty()) {
            book.setAuthors(bookDto.getAuthorDtoList()
                    .stream()
                    .map(authorDtoMapper::fromDto)
                    .collect(Collectors.toList()));
        }
//        if (bookDto.getCommentDtoList() != null && !bookDto.getCommentDtoList().isEmpty()) {
//            List<Comment> commentList = bookDto.getCommentDtoList()
//                    .stream()
//                    .map(commentDtoMapper::fromDto)
//                    .collect(Collectors.toList());
//        }
        return book;
    }
}
