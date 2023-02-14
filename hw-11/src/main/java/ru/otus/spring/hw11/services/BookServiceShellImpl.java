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

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookServiceShellImpl implements BookServiceShell {
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    GenreRepository genreRepository;
    CommentRepository commentRepository;

    @Override
    @Transactional
    public Long save(String name) {
        return bookRepository.save(
                        new Book(0L, name, new ArrayList<>(), null, new ArrayList<>())
                )
                .getId();
    }

    @Override
    @Transactional
    public Long saveBookWithGenreAndAuthor(String name, String authorName, String authorSurname, String genreName) {
        Optional<Book> optionalBook = bookRepository.findByName(name);
        Optional<Author> optionalAuthor = authorRepository.findByNameAndSurname(authorName, authorSurname);
        Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        if (optionalBook.isEmpty()) {
            Book book = new Book();
            book.setName(name);
            if (optionalAuthor.isEmpty()) {
                Author author = new Author(0L, authorName, authorSurname, new ArrayList<>());
                Author savedAuthor = authorRepository.save(author);
                book.addAuthor(savedAuthor);
            } else {
                book.addAuthor(optionalAuthor.get());
            }
            if (optionalGenre.isEmpty()) {
                Genre genre = new Genre(0L, genreName, new ArrayList<>());
                Genre savedGenre = genreRepository.save(genre);
                savedGenre.addBook(book);
            } else {
                optionalGenre.get().addBook(book);
            }
            Book savedBook = bookRepository.save(book);
            return savedBook.getId();
        } else {
            return optionalBook.orElseThrow(RuntimeException::new).getId();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        }
        System.out.println("Book id: " + id + " not found");
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findByName(String name) {
        Optional<Book> optionalBook = bookRepository.findByName(name);
        if (optionalBook.isPresent()) {
            return optionalBook.get().getId();
        }
        System.out.println("Book name: " + name + " not found");
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void addAuthor(Long id, String authorName, String authorSurname) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Author> optionalAuthor = authorRepository.findByNameAndSurname(authorName, authorSurname);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (optionalAuthor.isEmpty()) {
                Author author = new Author(0L, authorName, authorSurname, new ArrayList<>());
                Author savedAuthor = authorRepository.save(author);
                book.addAuthor(savedAuthor);
            } else {
                book.addAuthor(optionalAuthor.get());
            }
            bookRepository.update(book);
        } else {
            System.out.println("Book id: " + id + " not found");
        }

    }

    @Override
    @Transactional
    public void addGenre(Long id, String nameGenre) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Genre> optionalGenre = genreRepository.findByName(nameGenre);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (optionalGenre.isEmpty()) {
                Genre genre = new Genre(0L, nameGenre, new ArrayList<>());
                Genre savedGenre = genreRepository.save(genre);
                savedGenre.addBook(book);
            } else {
                optionalGenre.get().addBook(book);
            }
            bookRepository.update(book);
        } else {
        System.out.println("Book id: " + id + " not found");
    }
    }

    @Override
    @Transactional
    public void addCommentById(Long id, String commentText) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Comment comment = new Comment();
            comment.setTextComment(commentText);
            Comment savedComment = commentRepository.save(comment);
            book.addComment(savedComment);
            bookRepository.update(book);
        } else {
            System.out.println("Book id: " + id + " not found");
        }
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String changedName) {
        bookRepository.updateNameById(id, changedName);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
