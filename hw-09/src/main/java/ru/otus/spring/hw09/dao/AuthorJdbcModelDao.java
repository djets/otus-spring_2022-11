package ru.otus.spring.hw09.dao;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorJdbcModelDao<Author> implements ModelDao<Author> {
    JdbcOperations jdbc;
    NamedParameterJdbcOperations operations;

    public AuthorJdbcModelDao(NamedParameterJdbcOperations operations) {
        this.jdbc = operations.getJdbcOperations();
        this.operations = operations;
    }

    @Override
    public void insert(Author author) {

    }

    @Override
    public Author getById(Class<? extends Number> id) {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public void deleteById(Class<? extends Number> id) {

    }
}

