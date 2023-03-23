package ru.otus.spring.hw18.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw18.model.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    List<Author> findByNameAndSurname(String authorName, String authorSurname);
    List<Author> findByName(String authorName);
    List<Author> findBySurname (String authorSurname);
}
