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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
    public AuthorRepositoryImplTest(EntityManager em, TestEntityManager tem){
        this.repository = new AuthorRepositoryImpl(em);
        this.tem = tem;
    }

    @BeforeEach
    @Transactional
    void setUp() {
        author = new Author(0L, "Test Name", "Test Surname", null);
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
        Optional<Author> optionalGenre = repository.findByNameAndSurname(author.getName(), author.getSurname());
        assertThat(optionalGenre.orElseThrow().getName()).isEqualTo(author.getName());
        assertThat(optionalGenre.orElseThrow().getSurname()).isEqualTo(author.getSurname());
    }

    @Test
    @DisplayName("should update the name by id")
    @Transactional
    void shouldUpdateNameById() {
        repository.updateNameById(author.getId(), "Updated name");
        tem.detach(author);
        Optional<Author> updatedAuthor = repository.findById(author.getId());
        logger.info("id: {}, name: {}", updatedAuthor.orElseThrow().getId(), updatedAuthor.get().getName());
        assertThat(updatedAuthor).isNotEmpty();
        assertThat(updatedAuthor.get().getName()).isEqualTo("Updated name");
    }

    @Test
    void shouldUpdateSurnameById() {
        repository.updateSurnameById(author.getId(), "Updated surname");
        tem.detach(author);
        Optional<Author> updatedAuthor = repository.findById(author.getId());
        logger.info("id: {}, surname: {}", updatedAuthor.orElseThrow().getId(), updatedAuthor.get().getSurname());
        assertThat(updatedAuthor).isNotEmpty();
        assertThat(updatedAuthor.orElseThrow().getSurname()).isEqualTo("Updated surname");
    }

    @Test
    @DisplayName("should update")
    @Transactional
    void shouldUpdateAuthor() {
        author.setName("Updated name");
        author.setSurname("Updated surname");
        repository.update(author);
        Optional<Author> updatedAuthor = repository.findById(author.getId());
        assertThat(updatedAuthor).isNotEmpty();
        assertThat(updatedAuthor.orElseThrow().getName()).isEqualTo("Updated name");
        assertThat(updatedAuthor.orElseThrow().getSurname()).isEqualTo("Updated surname");
    }

    @Test
    @DisplayName("author should be deleted by id")
    @Transactional
    void shouldDeletedAuthorById() {
        repository.deleteById(101L);
        Optional<Author> optionalAuthor = repository.findById(101L);
        assertThatThrownBy(() -> optionalAuthor.orElseThrow(() -> new RuntimeException("Not found")))
                .hasMessage("Not found");
    }

    @Test
    @DisplayName("author should be deleted")
    @Transactional
    void shouldDeletedAuthor() {
        repository.delete(author);
        Optional<Author> optionalAuthor = repository.findById(author.getId());
        assertThat(optionalAuthor).isEmpty();
        assertThatThrownBy(() -> optionalAuthor.orElseThrow(() -> new RuntimeException("Not found")))
                .hasMessage("Not found");
    }
}