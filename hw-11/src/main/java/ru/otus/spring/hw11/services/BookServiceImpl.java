package ru.otus.spring.hw11.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.dao.AuthorRepository;
import ru.otus.spring.hw11.dao.BookRepository;
import ru.otus.spring.hw11.dao.CommentRepository;
import ru.otus.spring.hw11.dao.GenreRepository;
import ru.otus.spring.hw11.model.Author;
import ru.otus.spring.hw11.model.Book;
import ru.otus.spring.hw11.model.Comment;
import ru.otus.spring.hw11.model.Genre;

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

    @Override
    @Transactional
    public Long save(String name) {
        return repository.save(
                        new Book(null, name, new ArrayList<>(), null, new ArrayList<>())
                )
                .getId();
    }

    @Override
    @Transactional
    public Long saveBookWithGenreAndAuthor(String name, String authorName, String authorSurname, String genreName) {
        List<Book> optionalBook = repository.findByName(name);
        List<Author> optionalAuthor = authorRepository.findByNameAndSurname(authorName, authorSurname);
        List<Genre> optionalGenre = genreRepository.findByName(genreName);
        if (optionalBook.isEmpty()) {
            Book book = new Book(null, name, new ArrayList<>(), null, new ArrayList<>());
            if (optionalAuthor.isEmpty()) {
                Author author = new Author(0L, authorName, authorSurname, new ArrayList<>());
                Author savedAuthor = authorRepository.save(author);
                book.getAuthors().add(savedAuthor);
            } else {
                optionalAuthor.forEach(author -> book.getAuthors().add(author));
            }
            if (optionalGenre.isEmpty()) {
                Genre genre = new Genre(null, genreName, new ArrayList<>());
                Genre savedGenre = genreRepository.save(genre);
                savedGenre.getBooks().add(book);
            } else {
                optionalGenre.forEach(genre -> genre.getBooks().add(book));
            }
            Book savedBook = repository.save(book);
            return savedBook.getId();
        } else {
            return optionalBook.get(0).getId();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id, boolean loadComments) {
        if (loadComments) {
            Optional<Book> optionalBook = repository.findById(id);
            optionalBook.ifPresent(book -> book.getComments().forEach(Comment::getTextComment));
            return optionalBook.orElse(null);
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findByName(String name) {
        return repository.findByName(name).stream()
                .map(Book::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void addAuthor(Long id, String authorName, String authorSurname) {
        Optional<Book> optionalBook = repository.findById(id);
        List<Author> authors = authorRepository.findByNameAndSurname(authorName, authorSurname);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (authors.isEmpty()) {
                Author author = new Author(null, authorName, authorSurname, new ArrayList<>());
                author.getBooks().add(book);
                Author savedAuthor = authorRepository.save(author);
                book.getAuthors().add(savedAuthor);
            } else {
                authors.forEach(author -> book.getAuthors().add(author));
            }
            repository.update(book);
        }
    }

    @Override
    @Transactional
    public void addGenre(Long id, String nameGenre) {
        Optional<Book> optionalBook = repository.findById(id);
        List<Genre> genres = genreRepository.findByName(nameGenre);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (genres.isEmpty()) {
                Genre genre = new Genre(null, nameGenre, new ArrayList<>());
                genre.getBooks().add(book);
                Genre savedGenre = genreRepository.save(genre);
                savedGenre.getBooks().add(book);
                book.setGenre(genre);
            } else {
                genres.forEach(genre -> genre.getBooks().add(book));
            }
            repository.update(book);
        }
    }

    @Override
    @Transactional
    public void addCommentById(Long id, String commentText) {
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Comment comment = new Comment();
            comment.setTextComment(commentText);
            Comment savedComment = commentRepository.save(comment);
            book.getComments().add(savedComment);
            repository.update(book);
        }
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String changedName) {
        repository.findById(id).ifPresent(author -> {
            author.setName(changedName);
            repository.update(author);
        });

    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
