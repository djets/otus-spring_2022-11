package ru.otus.spring.hw11.services;

import ru.otus.spring.hw11.model.Comment;

import java.util.List;

public interface CommentsServiceShell {
    List<Comment> findAllByBook_BookId(Long id);
    void delete(Long id);
}
