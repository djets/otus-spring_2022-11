package ru.otus.spring.hw18.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
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

    @Override
    public String save(String name) {
        return repository.save(new Book(null, name, null, null, null)).get_id();
    }

    @Override
    public String saveBookWithGenreAndAuthor(String name, String authorName, String authorSurname, String genreName) {
        List<Book> optionalBook = repository.findByName(name);
        List<Author> optionalAuthor = authorRepository.findByNameAndSurname(authorName, authorSurname);
        List<Genre> optionalGenre = genreRepository.findByName(genreName);
        if (optionalBook.isEmpty()) {
            Book book = new Book(null, name, new ArrayList<>(), null, new ArrayList<>());
            Book savedBook = repository.save(book);

            if (optionalAuthor.isEmpty()) {
                Author author = new Author(null, authorName, authorSurname, List.of(savedBook));
                authorRepository.save(author);
                book.getAuthors().add(author);
            } else {
                optionalAuthor.forEach(author -> book.getAuthors().add(author));
                savedBook.getAuthors().addAll(optionalAuthor);
            }
            if (optionalGenre.isEmpty()) {
                Genre genre = new Genre(null, genreName, List.of(savedBook));
                genreRepository.save(genre);
                book.setGenre(genre);
            } else {
                optionalGenre.forEach(genre -> genre.getBooks().add(book));
                savedBook.setGenre(optionalGenre.stream().findFirst().orElse(null));
            }
            repository.save(savedBook);
            return savedBook.get_id();
        } else {
            return optionalBook.get(0).get_id();
        }
    }

    @Override
    public Book findById(String _id) {
        return repository.findById(_id).orElse(null);
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
                Author savedAuthor = authorRepository.save(author);
                book.getAuthors().add(savedAuthor);
            } else {
                authors.forEach(author -> {
                    if (book.getAuthors() !=null) {
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
                Genre savedGenre = genreRepository.save(genre);
                savedGenre.getBooks().add(book);
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
}
