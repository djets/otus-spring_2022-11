package ru.otus.spring.hw15.dao;

import ru.otus.spring.hw15.model.Comment;

import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);

    void update(Comment comment);

    void delete(Comment comment);
}
