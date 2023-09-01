package ru.otus.spring.hw24.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.hw24.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestPropertySource(properties = "mongock.enabled=false")
@Import(GenreServiceImpl.class)
@DisplayName("Класс GenreServiceImpl должен")
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @Test
    @DisplayName("добавлять жанр")
    void shouldAddGenre() {
        String name = "new genre";
        String id = genreService.save(name);
        assertThat(id).isNotBlank();

        Genre actualGenre = genreService.findById(id);

        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("ищет жанр по id")
    void shouldFindGenreById() {
        String name = "new genre";
        String id = genreService.save(name);
        Genre actualGenre = genreService.findById(id);

        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("ищет жанр по имени")
    void shouldFindGenreByName() {
        String name = "new genre";
        String id = genreService.save(name);
        List<String> actualGenresIds = genreService.findByName(name);

        assertThat(actualGenresIds).isNotEmpty();
        assertThat(actualGenresIds).contains(id);
    }

    @Test
    @DisplayName("возвращает все жанры")
    void shouldFindAllGenres() {
        String name1 = "genre1";
        String name2 = "genre2";
        String name3 = "genre3";
        genreService.save(name1);
        genreService.save(name2);
        genreService.save(name3);

        List<Genre> actualGenres = genreService.findAll();

        assertThat(actualGenres).isNotNull();
        assertThat(actualGenres).hasSize(3);
        assertThat(actualGenres).extracting(Genre::getName).containsExactlyInAnyOrder(name1, name2, name3);
    }

    @Test
    @DisplayName("обновляет имя жанра по id")
    void shouldUpdateGenreNameById() {
        String name = "new genre";
        String newName = "new genre name";
        String id = genreService.save(name);

        genreService.updateNameById(id, newName);

        Genre actualGenre = genreService.findById(id);
        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("удаляет жанр по id")
    void shouldDeleteGenreById() {
        String name = "new genre";
        String id = genreService.save(name);

        genreService.delete(id);

        Genre actualGenre = genreService.findById(id);
        assertThat(actualGenre).isNull();
    }
}
