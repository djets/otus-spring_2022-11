package ru.otus.spring.hw15.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw15.model.Comment;

import java.util.List;

public interface CommentsService {

    void delete(String _id);

    @Transactional
    List<Comment> findAllByBookId(String _id);
}
