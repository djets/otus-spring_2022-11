package ru.otus.spring.hw09.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;
import ru.otus.spring.hw09.services.AuthorService;
import ru.otus.spring.hw09.services.BookService;
import ru.otus.spring.hw09.services.GenreService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookCommands {
    AuthorService authorService;
    BookService bookService;
    GenreService genreService;

    @ShellMethod(value = "Create Book", key = {"cb", "create-book"})
    public String createBook(@ShellOption String bookName) {
        Long saveIdBook = bookService.saveIfAbsentName(bookName);
        return "Result: create Book " + bookService.getById(saveIdBook).getName();
    }

    @ShellMethod(value = "Create Book", key = {"-cb", "--create-book"})
    public String createBook(@ShellOption(help = "Input Book first Name") String bookName,
                             @ShellOption(help = "Input Author first Name") String authorFirstName,
                             @ShellOption(help = "Input Author last Name") String authorLastName,
                             @ShellOption(help = "Input Genre name of Book") String genreName) {
        Book book = new Book();
        for (Book b : bookService.getAll()) {
            if (b.getName().equals(bookName)) {
                book.setId(b.getId());
                book.setName(b.getName());
            }
        }
        book.setId(UUID.randomUUID().getMostSignificantBits());
        book.setName(bookName);
        book.setAuthor(authorService.getById(
                authorService.saveIfAbsentFirstNameLastName(authorFirstName, authorLastName)));
        book.setGenre(genreService.getById(
                genreService.saveIfAbsentName(genreName)));
        bookService.save(book);
        return "Result: create Book " + book.getName();
    }

    @ShellMethod(value = "Get all Books", key = {"-gbs", "--get-books"})
    public String getBooks() {
        List<Book> books = bookService.getAll();
        return books.stream()
                .map(book -> book.getName() + " id: " + book.getId() +
                        ", author: " + book.getAuthor().getFirstName().subSequence(0, 1) +
                        ". " + book.getAuthor().getLastName() +
                        ", genre: " + book.getGenre().getName())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Get Book by id", key = {"-b", "--get-book"})
    public String getBookById(Long id) {
        Book book = bookService.getById(id);
        return book.getId() + " " + book.getName();
    }

    @ShellMethod(value = "Edit Book by id", key = {"-eb", "--edit-book"})
    public String editBook(
            @ShellOption(help = "Input id Book") Long id,
            @ShellOption(help = "Input first and last Name Author") String authorName,
            @ShellOption(help = "Input Name Genre") String genreName
    ){
        Book book = bookService.getById(id);
        String[] strings = authorName.split(" ");
        Author author = authorService.getById(authorService.saveIfAbsentFirstNameLastName(strings[0], strings[1]));
        Genre genre = genreService.getById(genreService.saveIfAbsentName(genreName));
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.save(book.getId(), book);
        return "Result - id: " + book.getId() +
                ", author: " + book.getAuthor().getFirstName().subSequence(0, 1) +
                ". " + book.getAuthor().getLastName() +
                ", genre: " + book.getGenre().getName();
    }
}
