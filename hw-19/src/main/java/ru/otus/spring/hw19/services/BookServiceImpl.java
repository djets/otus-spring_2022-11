package ru.otus.spring.hw19.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw19.controller.exception.NotFoundException;
import ru.otus.spring.hw19.dto.BookDto;
import ru.otus.spring.hw19.dto.GenreDto;
import ru.otus.spring.hw19.dto.mapper.BookDtoMapper;
import ru.otus.spring.hw19.model.Book;
import ru.otus.spring.hw19.repository.BookRepository;

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
            List<GenreDto> foundedIdGenreList = genreService.findByName(book.getGenre().getName());
            if (foundedIdGenreList != null && !foundedIdGenreList.isEmpty()) {
                book.getGenre().set_id(
                        foundedIdGenreList.stream()
                                .findFirst()
                                .orElseThrow(NotFoundException::new)
                                .getId());
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
        Book saveBook = repository.save(book);
        return saveBook.get_id();
    }
}
