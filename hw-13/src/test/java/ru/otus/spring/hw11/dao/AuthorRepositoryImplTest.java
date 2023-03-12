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

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DisplayName("AuthorRepositoryImpl")
@TestPropertySource(locations = "classpath:application-test.yml")
@FieldDefaults(level = AccessLevel.PRIVATE)
class AuthorRepositoryImplTest {
    final Logger logger = LoggerFactory.getLogger(GenreRepositoryImplTest.class);
    final TestEntityManager tem;

    final AuthorRepository repository;
    Author author;

    @Autowired
    public AuthorRepositoryImplTest(AuthorRepository repository, TestEntityManager tem){
        this.repository = repository;
        this.tem = tem;
    }

    @BeforeEach
    @Transactional
    void setUp() {
        author = new Author(null, "Test Name", "Test Surname", null);
        tem.persist(author);
        tem.flush();
    }

    @Test
    @DisplayName("should save author to the database")
    @Transactional
    void shouldSaveAuthor() {
        Author expectedAuthor = new Author(0L, "Test new Name", "Test new Surname", null);
        Author savedAuthor = repository.save(expectedAuthor);
        logger.info("save author name: {}, surname: {}", savedAuthor.getName(), savedAuthor.getSurname());
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isGreaterThan(0L);
        assertThat(savedAuthor.getName()).isEqualTo("Test new Name");
        assertThat(savedAuthor.getSurname()).isEqualTo("Test new Surname");
    }

    @Test
    @DisplayName("should find author by id")
    @Transactional(readOnly = true)
    void shouldFindAuthorById() {
        Optional<Author> foundAuthor = repository.findById(author.getId());
        assertThat(foundAuthor).isNotEmpty();
        assertThat(foundAuthor.orElseThrow().getId()).isEqualTo(author.getId());
        assertThat(foundAuthor.orElseThrow().getName()).isEqualTo(author.getName());
        assertThat(foundAuthor.orElseThrow().getSurname()).isEqualTo(author.getSurname());
    }

    @Test
    @DisplayName("should return empty optional if no genre is found")
    @Transactional(readOnly = true)
    void shouldReturnEmptyOptionalForNonExistentGenre() {
        Optional<Author> foundAuthor = repository.findById(999L);
        assertThat(foundAuthor).isEmpty();
    }

    @Test
    @DisplayName("should find all authors")
    @Transactional(readOnly = true)
    void shouldFindAll() {
        List<Author> authorList = repository.findAll();
        assertThat(authorList.size()).isNotNull();
        assertThat(authorList.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("should find by name and surname")
    @Transactional(readOnly = true)
    void findByNameAndSurname() {
        List<Author> foundAuthors = repository.findByNameAndSurname(author.getName(), author.getSurname());
        assertThat(foundAuthors.get(0).getName()).isEqualTo(author.getName());
        assertThat(foundAuthors.get(0).getSurname()).isEqualTo(author.getSurname());
    }

    @Test
    @DisplayName("should update")
    @Transactional
    void shouldUpdateAuthor() {
        author.setName("Updated name");
        author.setSurname("Updated surname");
        repository.save(author);
        Optional<Author> updatedAuthor = repository.findById(author.getId());
        assertThat(updatedAuthor).isNotEmpty();
        assertThat(updatedAuthor.orElseThrow().getName()).isEqualTo("Updated name");
        assertThat(updatedAuthor.orElseThrow().getSurname()).isEqualTo("Updated surname");
    }

    @Test
    @DisplayName("author should be deleted")
    @Transactional
    void shouldDeletedAuthor() {
        repository.findById(101L).ifPresent(repository::delete);
        Optional<Author> optionalAuthor = repository.findById(101L);
        assertThat(optionalAuthor).isEmpty();
    }
}