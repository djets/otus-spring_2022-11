package ru.otus.spring.hw11.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw11.model.Author;
import ru.otus.spring.hw11.services.AuthorServiceShell;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorCommands {
    AuthorServiceShell authorService;

    @ShellMethod(value = "Create author", key = "-ca")
    public String createAuthor(@ShellOption String nameAuthor, @ShellOption String surnameAuthor) {
        return "Saved author id: " + authorService.save(nameAuthor, surnameAuthor);
    }

    @ShellMethod(value = "Find author by id", key = "-a")
    public String findAuthorById(@ShellOption(help = "Input id author") Long id) {
        Author author = authorService.findById(id);
        if (author != null) {
            return "Found author id: " + author.getId() +
                    ", name: " + author.getName() +
                    ", surname: " + author.getSurname();
        }
        return "Author not found";
    }

    @ShellMethod(value = "Find all authors", key = "-fa")
    public String findAuthors() {
        List<Author> authors = authorService.findAll();
        return authors.stream()
                .map(author -> author.getName() + " " + author.getSurname() +
                        " id: " + author.getId())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit name author by id", key = "-ena")
    public void editNameAuthor(
            @ShellOption(help = "Input id author") Long id,
            @ShellOption(help = "Input new name author") String updatedName
    ) {
        authorService.updateNameAuthor(id, updatedName);
    }

    @ShellMethod(value = "Edit surname author by id", key = "-esa")
    public void editSurnameAuthor(
            @ShellOption(help = "Input id author") Long id,
            @ShellOption(help = "Input new surname author") String updatedSurname
    ) {
        authorService.updateSurnameAuthor(id, updatedSurname);
    }

    @ShellMethod(value = "Delete author by id", key = "-da")
    public void deleteById(
            @ShellOption(help = "Input id author") Long id
    ) {
        authorService.delete(id);
    }
}
