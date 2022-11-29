package ru.otus.spring.dao;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {
    Optional<List<T>> getAll() throws IOException, CsvException;
    Optional<T> get(String text);
}
