package ru.otus.spring.hw18.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw18.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBook__id(String book_id);
    List<Comment> findTop10ByBook__id(String book_id);
}

