package ru.otus.spring.hw11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw11.model.Book;
import ru.otus.spring.hw11.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByBook(Book book);
}
