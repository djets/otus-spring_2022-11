package ru.otus.spring.hw11.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.model.Author;
import ru.otus.spring.hw11.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUtil;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("BookRepositoryImpl")
@TestPropertySource(locations = "classpath:application-test.yml")
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookRepositoryImplTest {

    final Logger logger = LoggerFactory.getLogger(GenreRepositoryImplTest.class);
    EntityManagerFactory emf;
    final TestEntityManager tem;

    final BookRepository repository;
    Book book;

    @Autowired
    public BookRepositoryImplTest(EntityManagerFactory emf, EntityManager em, TestEntityManager tem){
        this.repository = new BookRepositoryImpl(em);
        this.emf = emf;
        this.tem = tem;
    }

    @BeforeEach
    @Transactional
    void setUp() {
        book = new Book(0L, "Test Name", null, null, null);
        tem.persist(book);
        tem.flush();
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    @DisplayName("should find book by id with genre and authors")
    @Transactional(readOnly = true)
    void shouldFindByIdWithGenreAndAuthors() {
        Optional<Book> foundBook = repository.findById(102L);

        PersistenceUtil pu = emf.getPersistenceUnitUtil();
        logger.info("Book.genre loaded: {}", pu.isLoaded(book, "genre"));
        assertThat(pu.isLoaded(book, "genre")).isTrue();
        logger.info("Book.authors loaded: {}", pu.isLoaded(book, "authors"));
        assertThat(pu.isLoaded(book, "authors")).isTrue();

        assertThat(foundBook).isNotEmpty();
        assertThat(foundBook.orElseThrow().getId()).isEqualTo(102L);
        assertThat(foundBook.orElseThrow().getName()).isEqualTo("Alice in Wonderland");
        assertThat(foundBook.orElseThrow().getGenre().getId()).isEqualTo(104L);
        assertThat(foundBook.orElseThrow().getGenre().getName()).isEqualTo("Fantasy");
        assertThat(foundBook.orElseThrow().getAuthors().get(0).getId()).isEqualTo(102L);
        assertThat(foundBook.orElseThrow().getAuthors().get(0).getName()).isEqualTo("Lewis");
        assertThat(foundBook.orElseThrow().getAuthors().get(0).getSurname()).isEqualTo("Carroll");


    }

    @Test
    void findAll() {
    }

    @Test
    void findByName() {
    }

    @Test
    void updateNameById() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }
}