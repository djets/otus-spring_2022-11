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
import ru.otus.spring.hw11.model.Comment;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@DisplayName("CommentRepositoryImpl")
@TestPropertySource(locations = "classpath:application-test.yml")
@FieldDefaults(level = AccessLevel.PRIVATE)
class CommentRepositoryImplTest {
    final Logger logger = LoggerFactory.getLogger(GenreRepositoryImplTest.class);
    final TestEntityManager tem;
    final CommentRepository repository;
    Comment comment;

    @Autowired
    public CommentRepositoryImplTest (EntityManager em, TestEntityManager tem) {
        this.repository = new CommentRepositoryImpl(em);
        this.tem = tem;
    }

    @BeforeEach
    void setUp() {
        comment = new Comment(null, "This is a test comment", null, null);
        tem.persist(comment);
        tem.flush();
    }

    @Test
    @DisplayName("should save comment to the database")
    @Transactional
    void shouldSaveComment() {
        Comment expectedComment = new Comment(null, "This is a new test comment", null, null);
        Comment savedComment = repository.save(expectedComment);
        logger.info("save comment: {}, id: {}", savedComment.getTextComment(), savedComment.getId());
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isGreaterThan(0L);
        assertThat(savedComment.getTextComment()).isEqualTo("This is a new test comment");
    }

    @Test
    @DisplayName("should find comment by id")
    @Transactional(readOnly = true)
    void shouldFindCommentById() {
        Optional<Comment> foundComment = repository.findById(comment.getId());
        assertThat(foundComment).isNotEmpty();
        assertThat(foundComment.orElseThrow().getId()).isEqualTo(comment.getId());
        assertThat(foundComment.orElseThrow().getTextComment()).isEqualTo(comment.getTextComment());
    }

    @Test
    @DisplayName("should return empty optional if no comment is found")
    @Transactional(readOnly = true)
    void shouldReturnEmptyOptionalForNonExistentComment() {
        Optional<Comment> foundComment = repository.findById(999L);
        assertThat(foundComment).isEmpty();
    }

    @Test
    @DisplayName("should update comment")
    @Transactional
    void shouldUpdateComment() {
        comment.setTextComment("This is an updated test comment");
        repository.update(comment);
        Optional<Comment> updatedComment = repository.findById(comment.getId());
        assertThat(updatedComment).isNotEmpty();
        assertThat(updatedComment.orElseThrow().getTextComment()).isEqualTo("This is an updated test comment");
    }

    @Test
    @DisplayName("should delete comment")
    @Transactional
    void shouldDeleteComment() {
        repository.findById(101L).ifPresent(repository::delete);
        Optional<Comment> deletedCommentOptional = repository.findById(101L);
        assertThat(deletedCommentOptional).isEmpty();
    }
}