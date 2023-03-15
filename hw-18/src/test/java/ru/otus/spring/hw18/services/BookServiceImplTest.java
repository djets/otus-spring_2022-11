package ru.otus.spring.hw18.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.hw18.model.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestPropertySource(properties = "mongock.enabled=false")
@Import(BookServiceImpl.class)
@DisplayName("Класс BookServiceImpl должен")
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("добавлять книгу")
    void shouldSaveBook() {
        String bookName = "Treasure Island";
        String bookId = bookService.save(bookName);
        assertThat(bookId).isNotBlank();

        Book actualBook = bookService.findById(bookId);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getName()).isEqualTo(bookName);
    }

    @Test
    @DisplayName("добавлять книгу с жанром и авторами")
    void shouldSaveBookWithGenresAndAuthors() {
        String bookName = "War and Peace";
        String authorName = "Lev";
        String authorSurname = "Tolstoy";
        String genreName = "Historical fiction";
        String bookId = bookService.saveBookWithGenreAndAuthor(
                bookName,
                authorName,
                authorSurname,
                genreName);

        Book actualBook = bookService.findById(bookId);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getName()).isEqualTo(bookName);
        assertThat(actualBook.getAuthors()).hasSize(1);
        assertThat(actualBook.getAuthors().get(0).getName()).isEqualTo(authorName);
        assertThat(actualBook.getAuthors().get(0).getSurname()).isEqualTo(authorSurname);
        assertThat(actualBook.getGenre()).isNotNull();
        assertThat(actualBook.getGenre().getName()).isEqualTo(genreName);
    }

    @Test
    @DisplayName("добавлять автора книги")
    void shouldAddAuthorToBook() {
        String bookName = "Alice in Wonderland";
        String authorName = "Lewis";
        String authorSurname = "Carroll";
        String bookId = bookService.save(bookName);

        bookService.addAuthor(bookId, authorName, authorSurname);

        Book actualBook = bookService.findById(bookId);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getName()).isEqualTo(bookName);
        assertThat(actualBook.getAuthors()).hasSize(1);
        assertThat(actualBook.getAuthors().get(0).getName()).isEqualTo(authorName);
        assertThat(actualBook.getAuthors().get(0).getSurname()).isEqualTo(authorSurname);
    }

    @Test
    @DisplayName("добавлять жанр книги")
    void shouldAddGenreToBook() {
        String bookName = "Robinson Crusoe";
        String genreName = "Realistic fiction";
        String bookId = bookService.save(bookName);

        bookService.addGenre(bookId, genreName);

        Book actualBook = bookService.findById(bookId);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getName()).isEqualTo(bookName);
        assertThat(actualBook.getGenre()).isNotNull();
        assertThat(actualBook.getGenre().getName()).isEqualTo(genreName);
    }
}
