package ru.otus.spring.hw15.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw15.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {}
