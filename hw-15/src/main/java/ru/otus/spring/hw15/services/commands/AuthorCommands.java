package ru.otus.spring.hw15.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw15.model.Author;
import ru.otus.spring.hw15.model.Book;
import ru.otus.spring.hw15.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorCommands {
    AuthorService authorService;

    @ShellMethod(value = "Create author", key = "-ca")
    public String createAuthor(@ShellOption String nameAuthor, @ShellOption String surnameAuthor) {
        return "Saved author id: " + authorService.save(nameAuthor, surnameAuthor);
    }

    @ShellMethod(value = "Find author by id", key = "-a")
    public String findAuthorById(@ShellOption(help = "Input id author") String _id) {
        Author author = authorService.findById(_id);
        if (author != null) {
            return "Found author id: " + author.get_id() +
                    ", name: " + author.getName() +
                    ", surname: " + author.getSurname();
        }
        return "Author not found";
    }

    @ShellMethod(value = "Find author by id with Books ", key = "-A")
    public String findAuthorWithBooksById(@ShellOption(help = "Input id author") String _id) {
        Author author = authorService.findById(_id);
        if (author != null) {
            return "Found author id: " + author.get_id() +
                    ", name: " + author.getName() +
                    ", surname: " + author.getSurname() +
                    "\nBooks:\n" + author.getBooks().stream()
                    .map(Book::getName)
                    .collect(Collectors.joining(", "));
        }
        return "Author not found";
    }

    @ShellMethod(value = "Find all authors", key = "-fa")
    public String findAuthors() {
        List<Author> authors = authorService.findAll();
        return "Found:\n" + authors.stream()
                .map(author -> author.getName() + " " + author.getSurname() +
                        " id: " + author.get_id())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit name author by id", key = "-ena")
    public void editNameAuthor(
            @ShellOption(help = "Input id author") String _id,
            @ShellOption(help = "Input new name author") String updatedName
    ) {
        authorService.updateNameAuthor(_id, updatedName);
    }

    @ShellMethod(value = "Edit surname author by id", key = "-esa")
    public void editSurnameAuthor(
            @ShellOption(help = "Input id author") String _id,
            @ShellOption(help = "Input new surname author") String updatedSurname
    ) {
        authorService.updateSurnameAuthor(_id, updatedSurname);
    }

    @ShellMethod(value = "Delete author by id", key = "-da")
    public void deleteById(
            @ShellOption(help = "Input id author") String _id
    ) {
        authorService.delete(_id);
    }
}
