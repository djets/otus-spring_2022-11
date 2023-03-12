package ru.otus.spring.hw11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw11.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);
}
