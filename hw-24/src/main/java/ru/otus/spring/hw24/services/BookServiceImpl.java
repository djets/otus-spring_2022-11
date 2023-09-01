package ru.otus.spring.hw24.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw24.controller.exception.NotFoundException;
import ru.otus.spring.hw24.dto.BookDto;
import ru.otus.spring.hw24.dto.CommentDto;
import ru.otus.spring.hw24.dto.mapper.BookDtoMapper;
import ru.otus.spring.hw24.dto.mapper.CommentDtoMapper;
import ru.otus.spring.hw24.model.Book;
import ru.otus.spring.hw24.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    BookRepository repository;

    AuthorService authorService;
    GenreService genreService;

    BookDtoMapper bookDtoMapper;
    CommentDtoMapper commentDtoMapper;

    @Override
    public BookDto findById(String id) {
        return bookDtoMapper.toDto(repository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public List<String> findByName(String title) {
        return repository.findByTitle(title).stream()
                .map(Book::get_id)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findAll() {
        return repository.findAll().stream().map(bookDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(String _id) {
        repository.findById(_id).ifPresent(repository::delete);
    }

    @Override
    public String save(BookDto bookDto) {
        Book book = bookDtoMapper.fromDto(bookDto);
        if (book.getGenre() != null) {
            List<String> foundedIdGenreList = genreService.findByName(book.getGenre().getName());
            if (foundedIdGenreList != null && foundedIdGenreList.size() == 1) {
                foundedIdGenreList.forEach(s -> book.getGenre().set_id(s));
                book.getGenre().set_id(foundedIdGenreList.get(0));
            }
        }
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            book.getAuthors()
                    .forEach(author -> {
                        if (author.get_id() == null) {
                            List<String> foundedIdAuthorList = authorService
                                    .findByNameAndSurname(author.getName(), author.getSurname());
                            if (foundedIdAuthorList != null) {
                                foundedIdAuthorList.forEach(author::set_id);
                            }
                        }
                    });
        }

        if (!book.getComments().isEmpty()) {
            book.getComments().forEach(comment -> comment.setBook(book));
        }
        Book saveBook = repository.save(book);
        return saveBook.get_id();
    }

    @Override
    public List<CommentDto> findCommentsByBookId(String bookId) {
        Book book = repository.findById(bookId).orElseThrow(NotFoundException::new);
        return book.getComments()
                .stream()
                .map(commentDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
