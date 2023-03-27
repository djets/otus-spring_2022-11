package ru.otus.spring.hw18.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw18.controller.exception.NotFoundException;
import ru.otus.spring.hw18.model.Author;
import ru.otus.spring.hw18.model.Book;
import ru.otus.spring.hw18.model.Comment;
import ru.otus.spring.hw18.model.Genre;
import ru.otus.spring.hw18.repository.AuthorRepository;
import ru.otus.spring.hw18.repository.BookRepository;
import ru.otus.spring.hw18.repository.CommentRepository;
import ru.otus.spring.hw18.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    BookRepository repository;
    AuthorRepository authorRepository;
    GenreRepository genreRepository;
    CommentRepository commentRepository;

    AuthorService authorService;
    GenreService genreService;
    CommentsService commentsService;

    @Override
    public Book findById(String _id) {
        return repository.findById(_id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<String> findByName(String name) {
        return repository.findByName(name).stream()
                .map(Book::get_id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        repository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public void addAuthor(String _id, String authorName, String authorSurname) {
        Optional<Book> optionalBook = repository.findById(_id);
        List<Author> authors = authorRepository.findByNameAndSurname(authorName, authorSurname);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (authors.isEmpty()) {
                Author author = new Author(null, authorName, authorSurname, new ArrayList<>());
                author.getBooks().add(book);
//                Author savedAuthor = authorRepository.save(author);
                book.getAuthors().add(author);
            } else {
                authors.forEach(author -> {
                            if (book.getAuthors() != null) {
                                book.getAuthors().add(author);
                            }
                            book.setAuthors(List.of(author));
                        }
                );
            }
            repository.save(book);
        }
    }

    @Override
    public void addGenre(String _id, String nameGenre) {
        Optional<Book> optionalBook = repository.findById(_id);
        List<Genre> genres = genreRepository.findByName(nameGenre);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (genres.isEmpty()) {
                Genre genre = new Genre(null, nameGenre, new ArrayList<>());
                genre.getBooks().add(book);
//                Genre savedGenre = genreRepository.save(genre);
//                savedGenre.getBooks().add(book);
                book.setGenre(genre);
            } else {
                genres.forEach(genre -> genre.getBooks().add(book));
            }
            repository.save(book);
        }
    }

    @Override
    public void addCommentById(String _id, String commentText) {
        Optional<Book> optionalBook = repository.findById(_id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Comment comment = new Comment();
            comment.setTextComment(commentText);
            comment.setBook(book);
            Comment savedComment = commentRepository.save(comment);
            book.getComments().add(savedComment);
            repository.save(book);
        }
    }

    @Override
    public void updateNameById(String _id, String changedName) {
        repository.findById(_id).ifPresent(author -> {
            author.setName(changedName);
            repository.save(author);
        });
    }

    @Override
    public void delete(String _id) {
        repository.findById(_id).ifPresent(repository::delete);
    }

    @Override
    public String save(Book book) {
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
                        if (author.get_id() != null) {
                            List<String> foundedIdAuthorList = authorService.findByNameAndSurname(author.getName(), author.getSurname());
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
