package ru.otus.spring.hw09.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DAO для работы с книгами должно")
@TestPropertySource(locations = "classpath:application-test.yml")
@JdbcTest
@Import(BookDaoImpl.class)
class BookJdbcModelDaoTest {
    @Autowired
    private BookDao bookDao;

    @DisplayName("сохранять книгу")
    @ParameterizedTest
    @CsvSource(value = {
            "Robinson Crusoe, null, null, null, null, null",
            "Robinson Crusoe, 3, Daniel, Defoe, null, null",
            "Robinson Crusoe, 3, Daniel, Defoe, 1, Classic",
    }, nullValues = "null")
    void shouldBeSaveBook(String name,
                          Long idAuthor, String nameAuthor, String surnameAuthor,
                          Long idGenre, String nameGenre) {
        Book expectedBook = new Book();
        expectedBook.setName(name);
        if (nameAuthor != null && surnameAuthor != null) {
            expectedBook.setAuthor(new Author(idAuthor, nameAuthor, surnameAuthor));
        }
        if (nameGenre != null) {
            expectedBook.setGenre(new Genre(idGenre, nameGenre));
        }
        Long receivedId = bookDao.insert(expectedBook);
        expectedBook.setId(receivedId);
        assertThat(bookDao.findById(receivedId)).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу")
    @Test
    void updateTest() {
        int update = bookDao.update(1L, Map.of("name", "ChangedName", "author_id", "3"));
        assertThat(1).isEqualTo(update);
        Book expectedBook = new Book(1L, "ChangedName", new Author(3L, "Daniel", "Defoe"), new Genre(2L, "Fantasy"));
        assertThat(bookDao.findById(1L)).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("искать по id")
    @Test
    void findById() {
        Book expectedBook = new Book(1L, "Treasure Island",
                new Author(1L, "Robert Louis", "Stevenson"),
                new Genre(2L, "Fantasy"));
        assertThat(bookDao.findById(1L)).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("искать по имени")
    @ParameterizedTest
    @CsvSource(value = {
            "1, Treasure Island",
            "2, Alice in Wonderland",
    }, nullValues = "null")
    void findByNameTest(Long id, String name) {
        List<Long> receivedListId = bookDao.findByName(name);
        Long expectedId = receivedListId.iterator().next();
        assertThat(id).isNotNull().isEqualTo(expectedId);
    }

    @DisplayName("находить все книги")
    @Test
    void findAll() {
        assertThat(Objects.requireNonNull(bookDao.findAll()).size()).isEqualTo(2);
    }

    @DisplayName("удалять книгу")
    @Test
    void delete() {
        int delete = bookDao.delete(1L);
        assertThat(1).isEqualTo(delete);
        assertThatThrownBy(() -> bookDao.findById(1L)).hasMessage("Book with this id was not found");
    }
}