package ru.otus.spring.hw09.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookJdbcModelDao<Book> implements ModelDao<Book>{
    JdbcOperations jdbc;
    NamedParameterJdbcOperations operations;

    public BookJdbcModelDao(NamedParameterJdbcOperations operations) {
        this.jdbc = operations.getJdbcOperations();
        this.operations = operations;
    }

    @Override
    public void insert(Book book) {

    }

    @Override
    public Book getById(Class<? extends Number> id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void deleteById(Class<? extends Number> id) {

    }
}
