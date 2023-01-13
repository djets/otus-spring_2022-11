package ru.otus.spring.hw09.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw09.model.Genre;
import ru.otus.spring.hw09.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreCommands {
    GenreService genreService;

    @ShellMethod(value = "Create Genre Books", key = {"-cg", "--create-fiction-books"})
    public String createGenre(@ShellOption String nameGenre) {
        genreService.save(nameGenre);
        return "======================================================================";
    }

    @ShellMethod(value = "Get Genre by id", key = {"-g", "--genre-books"})
    public String getGenreById(Long id) {
        Genre genre = genreService.getById(id);
        return "Result: " + genre.getId() + " " + genre.getName();
    }

    @ShellMethod(value = "Get all Genre", key = {"-ggs", "--get-fiction-books"})
    public String getGenres() {
        List<Genre> genres = genreService.getAll();
        return "Result: " + genres.stream()
                .map(genre -> genre.getName() + " id: " + genre.getId())
                .collect(Collectors.joining(";\n"));
    }

    public String editGenre(
            @ShellOption(help = "Input id genre") Long id,
            @ShellOption(help = "You can edit name Genre") String name
    ) {
        genreService.update(id, name);
        return "======================================================================";
    }
}
