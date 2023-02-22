package ru.otus.spring.hw11.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.model.Comment;

import java.util.List;

public interface CommentsService {

    void delete(Long id);

    @Transactional
    List<Comment> findAllByBookId(Long id);
}
