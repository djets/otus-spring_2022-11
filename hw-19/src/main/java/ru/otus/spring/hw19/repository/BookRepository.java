package ru.otus.spring.hw19.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw19.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String title);

    List<Book> findBooksByGenre__id(String genre_id);

    List<Book> findBooksByAuthors__id(String authors_id);
}
