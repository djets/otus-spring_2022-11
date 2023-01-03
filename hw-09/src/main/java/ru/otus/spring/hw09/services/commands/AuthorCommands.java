package ru.otus.spring.hw09.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.services.AuthorService;
import ru.otus.spring.hw09.services.BookService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.*;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorCommands {
    AuthorService authorService;
    BookService bookService;

    @ShellMethod(value = "Create author", key = {"-ca", "--create-author"})
    public String createAuthor(
            @ShellOption String authorFirstName,
            @ShellOption String authorLastName) {
        authorService.saveIfAbsentFirstNameLastName(authorFirstName, authorLastName);
        return "Result: create author" + authorFirstName + " " + authorFirstName;
    }

    @ShellMethod(value = "Get author by id", key = {"-a", "--author"})
    public String getAuthorById(@ShellOption(help = "Input id author") Long id) {
        Author author = authorService.getById(id);
        return "Result: " + author.getId() + " " + author.getFirstName() + " " + author.getLastName();
    }

    @ShellMethod(value = "Get all authors", key = {"-gas", "--get-authors"})
    public String getAuthors() {
        List<Author> authors = authorService.getAll();
        return authors.stream()
                .map(author -> author.getFirstName() + " " + author.getLastName() +
                        " id: " + author.getId())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit Author by id", key = {"-eab", "--edit-authors-books"})
    public String editAuthorsBooks(
            @ShellOption(help = "Input id author") Long id,
            @ShellOption(help = "If you have add Book, input book id ", defaultValue = "0") String addBookId,
            @ShellOption(help = "If you have remove Book, input book id ", defaultValue = "0") String removeBookId
    ) {
        Author author = authorService.getById(id);
        Book book = bookService.getById(Long.parseLong(addBookId));
        if (!(Long.parseLong(addBookId) == 0)) {
            author.getBooks().add(book);
        }
        if (!(Long.parseLong(removeBookId) == 0)) {
            book.setAuthor(null);
            bookService.save(book.getId(), book);
            author.getBooks().remove(book);
        }
        authorService.save(author.getId(), author);
        return "Result: " + author.getId() + " " + author.getFirstName() + " " + author.getLastName();
    }
    @ShellMethod(value = "Edit Author by id", key = {"-ea", "--edit-author"})
    public String editAuthor(
            @ShellOption(help = "Input id author") Long id,
            @ShellOption(help = "You can edit first name Author", defaultValue = "none") String firstNameAuthor,
            @ShellOption(help = "You can edit last name Author", defaultValue = "none") String lastNameAuthor
    ) {
        Author author = authorService.getById(id);
        if (!firstNameAuthor.equals("none")) {
            author.setFirstName(firstNameAuthor);
        }
        if (!lastNameAuthor.equals("none")) {
            author.setLastName(lastNameAuthor);
        }
        authorService.save(author.getId(), author);
        return "Result: " + author.getId() + " " + author.getFirstName() + " " + author.getLastName();
    }
}
