package ru.otus.spring.hw11.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw11.model.Book;
import ru.otus.spring.hw11.services.BookService;

import java.util.List;
import java.util.stream.Collectors;


@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookCommands {
    BookService bookService;

    @ShellMethod(value = "Create book", key = "-cb")
    public String createBook(@ShellOption(help = "Input book name") String name) {
        return "Saved book id: " + bookService.save(name);
    }

    @ShellMethod(value = "Create book with genre and author", key = "-CB")
    public String createBookWithGenreAndAuthor(
            @ShellOption(help = "Input book name") String bookName,
            @ShellOption(help = "Input author name") String authorName,
            @ShellOption(help = "Input author surname") String authorSurname,
            @ShellOption(help = "Input genre name of book") String genreName
    ) {
        return "Saved book id: " + bookService
                .saveBookWithGenreAndAuthor(bookName, authorName, authorSurname, genreName);
    }

    @ShellMethod(value = "Find book by id", key = "-b")
    public String findBookById(Long id) {
        Book book = bookService.findById(id, false);
        if (book != null) {
            return "Found book id: " + book.getId() + ", name: " + book.getName();
        }
        return "Book not found";
    }

    @ShellMethod(value = "Find book by id with all parameters", key = "-B")
    public String findBookWithAllParametersById(Long id) {
        Book book = bookService.findById(id, true);
        if (book != null) {
            return "Found book id: " + book.getId() + ", name: " + book.getName() +
                    "\nAuthors: " + book.getAuthors().stream()
                    .map(author -> author.getName() + " " + author.getSurname())
                    .collect(Collectors.joining(", ")) +
                    "\nGenre: " + book.getGenre().getName() +
                    "\nComments:\n" + book.getComments().stream()
                    .map(comment -> comment.getCreateData().toString().substring(0,19) +
                            " " + comment.getTextComment())
                    .collect(Collectors.joining("\n-------\n"));
        }
        return "Book not found";
    }

    @ShellMethod(value = "Find all books", key = "-fb")
    public String findAll() {
        List<Book> books = bookService.findAll();
        return "Found:\n" + books.stream()
                .map(book -> book.getName() + " id: " + book.getId())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit book name by id", key = "-eb")
    public void editAuthor(
            @ShellOption(help = "Input id book") Long id,
            @ShellOption(help = "Input new name book") String changedName
    ) {
        bookService.updateNameById(id, changedName);
    }

    @ShellMethod(value = "Add author book by id", key = "-aab")
    public void addAuthorBookById(
            @ShellOption(help = "Input id Book") Long id,
            @ShellOption(help = "Input name author") String nameAuthor,
            @ShellOption(help = "Input surname author") String surnameAuthor
    ) {
        bookService.addAuthor(id, nameAuthor, surnameAuthor);
    }

    @ShellMethod(value = "Add genre book by id", key = "-agb")
    public void addGenreBookById(
            @ShellOption(help = "Input id book") Long id,
            @ShellOption(help = "Input name genre") String nameGenre
    ) {
        bookService.addGenre(id, nameGenre);
    }

    @ShellMethod(value = "Add comment book by id", key = "-acb")
    public void addCommentBookById(
            @ShellOption(help = "Input id book") Long id,
            @ShellOption(help = "Input text comment") String textComment
    ) {
        bookService.addCommentById(id, textComment);
    }

    @ShellMethod(value = "Delete book by id", key = "-db")
    public void deleteById(
            @ShellOption(help = "Input id book") Long id
    ) {
        bookService.delete(id);
    }
}
