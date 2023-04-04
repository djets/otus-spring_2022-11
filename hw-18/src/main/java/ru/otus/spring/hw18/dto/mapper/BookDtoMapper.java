package ru.otus.spring.hw18.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw18.dto.BookDto;
import ru.otus.spring.hw18.model.Book;
import ru.otus.spring.hw18.model.Comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookDtoMapper implements DtoMapper<Book, BookDto> {
    CommentDtoMapper commentDtoMapper;
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
        Optional.ofNullable(book.getComments())
                .ifPresent(commentList -> {
                            if (!book.getComments().isEmpty()) {
                                bookDto.setCommentDtoList(
                                        commentList.stream()
                                                .map(commentDtoMapper::toDto)
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
            book.getGenre().setBooks(List.of(book));
        }
        if (bookDto.getAuthorDtoList() != null && !bookDto.getAuthorDtoList().isEmpty()) {
            book.setAuthors(bookDto.getAuthorDtoList()
                    .stream()
                    .map(authorDtoMapper::fromDto)
                    .collect(Collectors.toList()));
            book.getAuthors().forEach(author -> author.getBooks().add(book));
        }
        if (bookDto.getCommentDtoList() != null && !bookDto.getCommentDtoList().isEmpty()) {
            List<Comment> commentList = bookDto.getCommentDtoList()
                    .stream()
                    .map(commentDtoMapper::fromDto)
                    .collect(Collectors.toList());
            book.setComments(commentList);
            book.getComments().forEach(comment -> comment.setBook(book));
        }

        return book;
    }
}
