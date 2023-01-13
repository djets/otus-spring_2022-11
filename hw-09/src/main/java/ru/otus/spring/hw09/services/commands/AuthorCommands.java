package ru.otus.spring.hw09.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorCommands {
    AuthorService authorService;

    @ShellMethod(value = "Create author", key = {"-ca", "--create-author"})
    public String createAuthor(@ShellOption String nameAuthor, @ShellOption String surnameAuthor) {
        authorService.save(nameAuthor, surnameAuthor);
        return "======================================================================";
    }

    @ShellMethod(value = "Get author by id", key = {"-a", "--author"})
    public String getAuthorById(@ShellOption(help = "Input id author") Long id) {
        Author author = authorService.getById(id);
        return "Result: " + author.getId() + " " + author.getName() + " " + author.getSurname();
    }

    @ShellMethod(value = "Get all authors", key = {"-gas", "--get-authors"})
    public String getAuthors() {
        List<Author> authors = authorService.getAll();
        return authors.stream()
                .map(author -> author.getName() + " " + author.getSurname() +
                        " id: " + author.getId())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit author by id", key = {"-ea", "--edit-author"})
    public String editAuthor(
            @ShellOption(help = "Input id author") Long id,
            @ShellOption(help = "You can edit first name author", defaultValue = "__NULL__") String name,
            @ShellOption(help = "You can edit last name author", defaultValue = "__NULL__") String surname
    ) {
        authorService.update(id, name, surname);
        return "======================================================================";
    }
}
