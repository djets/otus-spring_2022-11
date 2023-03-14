package ru.otus.spring.hw15.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw15.model.Genre;
import ru.otus.spring.hw15.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent(value = "1. Genre commands")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreCommands {
    GenreService genreService;

    @ShellMethod(value = "Create genre", key = "-cg")
    public String createGenre(@ShellOption String nameGenre) {
        return "Saved genre id: " + genreService.save(nameGenre);
    }

    @ShellMethod(value = "Find genre by id", key = "-g")
    public String findGenreById(String _id) {
        Genre genre = genreService.findById(_id);
        if (genre != null) {
            return "Found genre id: " + genre.get_id() + ", name: " + genre.getName();
        }
        return "Genre not found";
    }

    @ShellMethod(value = "Find all genre", key = "-fg")
    public String findAll() {
        List<Genre> genres = genreService.findAll();
        return "Found:\n" + genres.stream()
                .map(genre -> genre.getName() + " id: " + genre.get_id())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit genre name by id", key = "-eg")
    public void editGenreNameById(
            @ShellOption(help = "Input id genre") String _id,
            @ShellOption(help = "Input new name Genre") String name
    ) {
        genreService.updateNameById(_id, name);
    }

    @ShellMethod(value = "Delete genre by id", key = "-dg")
    public void deleteById(
            @ShellOption(help = "Input id genre") String _id
    ) {
        genreService.delete(_id);
    }
}
