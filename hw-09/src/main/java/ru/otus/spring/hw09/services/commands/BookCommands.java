package ru.otus.spring.hw09.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.services.BookService;

import java.util.List;
import java.util.stream.Collectors;


@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookCommands {
    BookService bookService;

    @ShellMethod(value = "Create Book with all parameters", key = {"-cb", "--create-book-with-all"})
    public String createBook(@ShellOption(help = "Input book name") String bookName,
                             @ShellOption(help = "Input author name", defaultValue = "__NULL__") String authorName,
                             @ShellOption(help = "Input author surname", defaultValue = "__NULL__") String authorSurname,
                             @ShellOption(help = "Input genre name of book", defaultValue = "__NULL__") String genreName) {
        bookService.save(bookName, authorName, authorSurname, genreName);
        return "======================================================================";
    }

    @ShellMethod(value = "Get Book by id", key = {"-b", "--get-book"})
    public String getBookById(Long id) {
        Book book = bookService.getById(id);
        return "Result: " + book.getId() + " " + book.getName();
    }

    @ShellMethod(value = "Get all Books", key = {"-gbs", "--get-books"})
    public String getBooks() {
        List<Book> books = bookService.getAll();
        return "Result: " + books.stream()
                .map(book -> book.getName() + " id: " + book.getId() +
                        ", author: " + book.getAuthor().getName().subSequence(0, 1) +
                        ". " + book.getAuthor().getSurname() +
                        ", genre: " + book.getGenre().getName())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit Book by id", key = {"-eb", "--edit-book"})
    public String editBook(
            @ShellOption(help = "Input id Book") Long id,
            @ShellOption(help = "Input first and last Name Author", defaultValue = "__NULL__") String authorNameSurname,
            @ShellOption(help = "Input Name Genre", defaultValue = "__NULL__") String genreName
    ) {
        bookService.update(id, authorNameSurname, genreName);
        return "======================================================================";
    }
}
