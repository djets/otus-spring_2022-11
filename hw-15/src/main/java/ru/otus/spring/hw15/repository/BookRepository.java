package ru.otus.spring.hw15.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw15.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByName(String name);
}
