package ru.otus.spring.hw15.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw15.model.Author;
import ru.otus.spring.hw15.model.Book;
import ru.otus.spring.hw15.model.Comment;
import ru.otus.spring.hw15.services.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public String findBookById(String _id) {
        Book book = bookService.findById(_id);
        if (book != null) {
            return "Found book id: " + book.get_id() + ", name: " + book.getName();
        }
        return "Book not found";
    }

    @ShellMethod(value = "Find book by id with all parameters", key = "-B")
    public String findBookWithAllParametersById(String _id) {
        Book book = bookService.findById(_id);
        if (book != null) {
            return "Found book id: " + book.get_id() + ", name: " + book.getName() +
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
                .map(book -> book.getName() + " id: " + book.get_id())
                .collect(Collectors.joining(";\n"));
    }

    @ShellMethod(value = "Edit book name by id", key = "-eb")
    public void editAuthor(
            @ShellOption(help = "Input id book") String _id,
            @ShellOption(help = "Input new name book") String changedName
    ) {
        bookService.updateNameById(_id, changedName);
    }

    @ShellMethod(value = "Add author book by id", key = "-aab")
    public void addAuthorBookById(
            @ShellOption(help = "Input id Book") String _id,
            @ShellOption(help = "Input name author") String nameAuthor,
            @ShellOption(help = "Input surname author") String surnameAuthor
    ) {
        bookService.addAuthor(_id, nameAuthor, surnameAuthor);
    }

    @ShellMethod(value = "Add genre book by id", key = "-agb")
    public void addGenreBookById(
            @ShellOption(help = "Input id book") String _id,
            @ShellOption(help = "Input name genre") String nameGenre
    ) {
        bookService.addGenre(_id, nameGenre);
    }

    @ShellMethod(value = "Add comment book by id", key = "-acb")
    public void addCommentBookById(
            @ShellOption(help = "Input id book") String _id,
            @ShellOption(help = "Input text comment") String textComment
    ) {
        bookService.addCommentById(_id, textComment);
    }

    @ShellMethod(value = "Delete book by id", key = "-db")
    public void deleteById(
            @ShellOption(help = "Input id book") String _id
    ) {
        bookService.delete(_id);
    }
}
