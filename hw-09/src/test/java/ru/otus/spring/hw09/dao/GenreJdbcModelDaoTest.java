package ru.otus.spring.hw09.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.hw09.model.Genre;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DAO для работы с жанрами должно")
@TestPropertySource(locations = "classpath:application-test.yml")
@JdbcTest
@Import(GenreDaoImpl.class)
class GenreJdbcModelDaoTest {
    @Autowired
    GenreDao genreDao;

    @DisplayName("сохранять жанр")
    @Test
    void shouldBeSaveGenre() {
        Genre expectedGenre = new Genre();
        expectedGenre.setName("Test Genre");
        Long receivedId = genreDao.insert(expectedGenre);
        expectedGenre.setId(receivedId);
        assertThat(genreDao.findById(receivedId)).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("обновлять жанр")
    @Test
    void updateTest() {
        int update = genreDao.update(1L, "ChangedName");
        assertThat(1).isEqualTo(update);
        Genre expectedGenre = new Genre(1L, "ChangedName");
        assertThat(genreDao.findById(1L)).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("искать по id")
    @Test
    void findById() {
        Genre expectedGenre = new Genre(1L, "Classic");
        assertThat(genreDao.findById(1L)).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("искать по имени")
    @ParameterizedTest
    @CsvSource(value = {
            "1, Classic",
            "2, Fantasy",
    }, nullValues = "null")
    void findByNameTest(Long id, String name) {
        List<Long> receivedListId = genreDao.findByName(name);
        Long expectedId = receivedListId.iterator().next();
        assertThat(id).isNotNull().isEqualTo(expectedId);
    }

    @DisplayName("находить всех жанры")
    @Test
    void findAll() {
        assertThat(Objects.requireNonNull(genreDao.findAll()).size()).isEqualTo(2);
    }

    @DisplayName("удалять жанр")
    @Test
    void delete() {
        int delete = genreDao.delete(1L);
        assertThat(1).isEqualTo(delete);
        assertThatThrownBy(() -> genreDao.findById(1L)).hasMessage("Genre with this id was not found");
    }
}