package ru.otus.spring.hw11.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.model.Genre;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DisplayName("GenreRepositoryImpl")
@TestPropertySource(locations = "classpath:application-test.yml")
@FieldDefaults(level = AccessLevel.PRIVATE)
class GenreRepositoryImplTest {
    final Logger logger = LoggerFactory.getLogger(GenreRepositoryImplTest.class);
    final TestEntityManager tem;
    final GenreRepository repository;
    Genre genre;

    @Autowired
    public GenreRepositoryImplTest(EntityManager em, TestEntityManager tem) {
        this.repository = new GenreRepositoryImpl(em);
        this.tem = tem;
    }

    @BeforeEach
    @Transactional
    void setUp() {
        genre = new Genre(null, "Test Name", new ArrayList<>());
        tem.persist(genre);
        tem.flush();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Genre One",
            "Genre Two",
            "Genre Three"
    })
    @DisplayName("should save genre to the database")
    @Transactional
    void shouldSaveGenre(String name) {
        Genre expectedGenre = new Genre(null, name, new ArrayList<>());
        Genre savedGenre = repository.save(expectedGenre);
        logger.info("save genre: {}, {}", savedGenre.getName(), savedGenre.getId());
        assertThat(savedGenre).isNotNull();
        assertThat(savedGenre.getId()).isGreaterThan(0L);
        assertThat(savedGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "101",
            "102",
            "103",
            "104"
    })
    @DisplayName("should find by id")
    @Transactional(readOnly = true)
    void shouldFindById(Long id) {
        Optional<Genre> optionalGenre = repository.findById(id);
        assertThat(optionalGenre.orElseThrow(RuntimeException::new).getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("should return empty optional if no genre is found")
    @Transactional(readOnly = true)
    void shouldReturnEmptyOptionalForNonExistentGenre() {
        Optional<Genre> foundGenre = repository.findById(999L);
        assertThat(foundGenre).isEmpty();
    }

    @Test
    @DisplayName("should find all genres")
    @Transactional(readOnly = true)
    void shouldFindAll() {
        List<Genre> genreList = repository.findAll();
        assertThat(genreList.size()).isNotNull();
        assertThat(genreList.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("should find by name")
    @Transactional(readOnly = true)
    void shouldFindByName() {
        List<Genre> foundGenres = repository.findByName(genre.getName());
        assertThat(foundGenres.get(0).getName()).isEqualTo(genre.getName());
    }

    @Test
    @DisplayName("should update")
    @Transactional
    void shouldUpdateGenre() {
        genre.setName("Updated name");
        repository.update(genre);
        Optional<Genre> updatedGenre = repository.findById(genre.getId());
        assertThat(updatedGenre).isNotEmpty();
        assertThat(updatedGenre.orElseThrow().getName()).isEqualTo("Updated name");
    }

    @Test
    @DisplayName("genre should be deleted")
    @Transactional
    void shouldDeletedGenre() {
        repository.findById(101L).ifPresent(repository::delete);
        Optional<Genre> deletedGenreOptional = repository.findById(101L);
        assertThat(deletedGenreOptional).isEmpty();
    }
}