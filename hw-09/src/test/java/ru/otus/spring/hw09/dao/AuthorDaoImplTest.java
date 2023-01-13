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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DAO для работы с авторами должно")
@TestPropertySource(locations = "classpath:application-test.yml")
@JdbcTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    @Autowired
    AuthorDao authorDao;

    @DisplayName("сохранять автора")
    @Test
    void shouldBeSaveAuthorTest() {
        Author expectedAuthor = new Author(null, "John", "Escott");
        Long receivedId = authorDao.insert(expectedAuthor);
        expectedAuthor.setId(receivedId);
        assertThat(authorDao.findById(receivedId)).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("обновлять автора")
    @Test
    void updateTest() {
        int update = authorDao.update(1L, Map.of("name", "Change", "surname", "Test"));
        assertThat(1).isEqualTo(update);
        Author expectedAuthor = new Author(1L, "Change", "Test");
        assertThat(authorDao.findById(1L)).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("искать по id")
    @Test
    void findByIdTest() {
        Author expectedAuthor = new Author(1L, "Robert Louis", "Stevenson");
        assertThat(authorDao.findById(1L)).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("искать по имени")
    @ParameterizedTest
    @CsvSource(value = {
            "1, Robert Louis, Stevenson",
            "2, Lewis, Carroll",
    }, nullValues = "null")
    void findByNameTest(Long id, String name, String surname) {
        List<Long> receivedListId = authorDao.findByName(name, surname);
        Long expectedId = receivedListId.iterator().next();
        assertThat(id).isNotNull().isEqualTo(expectedId);
    }

    @DisplayName("находить всех авторов")
    @Test
    void findAllTest() {
        assertThat(Objects.requireNonNull(authorDao.findAll()).size()).isEqualTo(3);
    }

    @DisplayName("удалять автора")
    @Test
    void deleteTest() {
        int delete = authorDao.delete(1L);
        assertThat(1).isEqualTo(delete);
        assertThatThrownBy(() -> authorDao.findById(1L)).hasMessage("Author with this id was not found");
    }
}