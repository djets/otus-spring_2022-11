package ru.otus.spring.hw09.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;
import ru.otus.spring.hw09.services.BookService;
import ru.otus.spring.hw09.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreCommands {
    GenreService genreService;

    BookService bookService;

    @ShellMethod(value = "Create genre", key = {"-cg", "--create-genre"})
    public String createGenre(@ShellOption String genreName) {
        genreService.saveIfAbsentName(genreName);
        return "Result: create " + genreName;
    }

    @ShellMethod(value = "Get genre by id", key = {"-g", "--genre"})
    public String getGenreById(Long id) {
        Genre genre = genreService.getById(id);
        return genre.getId() + " " + genre.getName();
    }

    @ShellMethod(value = "Get all genres", key = {"-ggs", "--get-genres"})
    public String getGenres() {
        List<Genre> genres = genreService.getAll();
        return genres.stream()
                .map(genre -> genre.getName() + " id: " + genre.getId())
                .collect(Collectors.joining("; "));
    }

    public String editGenre(
            @ShellOption(help = "Input id genre") Long id,
            @ShellOption(help = "You can edit name Genre", defaultValue = "none") String name,
            @ShellOption(help = "If you have add Book, input book id ", defaultValue = "0") String addBookId,
            @ShellOption(help = "If you have remove Book, input book id ", defaultValue = "0") String removeBookId
    ) {
        Genre genre = genreService.getById(id);
        Book book = bookService.getById(Long.parseLong(addBookId));
        if (!name.equals("none")) {
            genre.setName(name);
        }
        if (!(Long.parseLong(addBookId) == 0)) {
            book.setGenre(genre);
            genre.getBooks().add(book);
            bookService.save(book.getId(), book);
        }
        if (!(Long.parseLong(addBookId) == 0)) {
            book.setGenre(null);
            bookService.save(book.getId(), book);
            genre.getBooks().remove(book);
        }
        genreService.save(genre.getId(), genre);
        return "Result: " + genre.getId() + " " + genre.getName();
    }
}
