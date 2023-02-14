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
import ru.otus.spring.hw11.model.Comment;
import ru.otus.spring.hw11.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
    @DisplayName("should save author to the database")
    @Transactional
    void shouldSave() {
        Author expectedAuthor = new Author(0L, "Test new Name", "Test new Surname", new ArrayList<>());
        Genre expectedGenre = new Genre(0L, "Test Genre", new ArrayList<>());
        Comment expectedComment = new Comment(0L, "This is a new test comment", null, null);
        Book expectedBook = new Book(0L, "Test Book", new ArrayList<>(), null, new ArrayList<>());
        expectedBook.addAuthor(expectedAuthor);
        expectedBook.addComment(expectedComment);
        expectedGenre.addBook(expectedBook);

        Book savedBook = repository.save(expectedBook);
        logger.info("save book id: {}, name: {}", savedBook.getId(), savedBook.getName());

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isGreaterThan(0L);
        assertThat(savedBook.getName()).isEqualTo("Test Book");
    }

    @Test
    @DisplayName("should find book by id")
    @Transactional(readOnly = true)
    void findById() {
        Optional<Book> foundBook = repository.findById(book.getId());
        assertThat(foundBook).isNotEmpty();
        assertThat(foundBook.orElseThrow().getId()).isEqualTo(book.getId());
        assertThat(foundBook.orElseThrow().getName()).isEqualTo(book.getName());
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
    @DisplayName("should find all books")
    @Transactional(readOnly = true)
    void shouldFindAll() {
        List<Book> authorList = repository.findAll();
        assertThat(authorList.size()).isNotNull();
        assertThat(authorList.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("should find by name")
    @Transactional(readOnly = true)
    void findByName() {
        Optional<Book> optionalGenre = repository.findByName(book.getName());
        assertThat(optionalGenre.orElseThrow().getName()).isEqualTo(book.getName());
    }

    @Test
    @DisplayName("should update the name by id")
    @Transactional
    void shouldUpdateNameById() {
        repository.updateNameById(book.getId(), "Updated name");
        tem.detach(book);
        Optional<Book> updateGenre = repository.findById(book.getId());
        logger.info("id: {}, name: {}", updateGenre.orElseThrow().getId(), updateGenre.get().getName());
        assertThat(updateGenre).isNotEmpty();
        assertThat(updateGenre.get().getName()).isEqualTo("Updated name");
    }

    @Test
    @DisplayName("should update")
    @Transactional
    void shouldUpdateGBook() {
        book.setName("Updated name");
        repository.update(book);
        Optional<Book> updatedGenre = repository.findById(book.getId());
        assertThat(updatedGenre).isNotEmpty();
        assertThat(updatedGenre.orElseThrow().getName()).isEqualTo("Updated name");
    }

    @Test
    @DisplayName("book should be deleted by id")
    @Transactional
    void shouldDeletedGenreById() {
        repository.deleteById(101L);
        Optional<Book> optionalGenre = repository.findById(101L);
        assertThatThrownBy(() -> optionalGenre.orElseThrow(() -> new RuntimeException("Not found")))
                .hasMessage("Not found");
    }

    @Test
    @DisplayName("genre should be deleted")
    @Transactional
    void shouldDeletedGenre() {
        repository.deleteById(101L);
        Optional<Book> optionalGenre = repository.findById(101L);
        assertThatThrownBy(() -> optionalGenre.orElseThrow(() -> new RuntimeException("Not found")))
                .hasMessage("Not found");
    }
}