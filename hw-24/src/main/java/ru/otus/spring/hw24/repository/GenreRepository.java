package ru.otus.spring.hw24.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw24.model.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByName(String name);
}
