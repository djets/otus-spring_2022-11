package ru.otus.spring.hw11.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw11.model.Genre;
import ru.otus.spring.hw11.services.GenreServiceShell;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent(value = "1. Genre commands")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreCommands {
    GenreServiceShell genreService;

    @ShellMethod(value = "Create genre", key = "-cg")
    public String createGenre(@ShellOption String nameGenre) {
        return "Saved genre id: " + genreService.save(nameGenre);
    }

    @ShellMethod(value = "Find genre by id", key = "-g")
    public String findGenreById(Long id) {
        Genre genre = genreService.findById(id);
        if (genre != null) {
            return "Found genre id: " + genre.getId() + ", name: " + genre.getName();
        }
        return "Genre not found";
    }

    @ShellMethod(value = "Find all genre", key = "-fg")
    public String findAll() {
        List<Genre> genres = genreService.findAll();
        return "Found:\n" + genres.stream()
                .map(genre -> genre.getName() + " id: " + genre.getId())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit genre name by id", key = "-eg")
    public void editGenreNameById(
            @ShellOption(help = "Input id genre") Long id,
            @ShellOption(help = "Input new name Genre") String name
    ) {
        genreService.updateNameById(id, name);
    }

    @ShellMethod(value = "Delete genre by id", key = "-dg")
    public void deleteById(
            @ShellOption(help = "Input id genre") Long id
    ) {
        genreService.delete(id);
    }
}