package ru.otus.spring.dao;

import ru.otus.spring.model.Question;

import java.util.List;
import java.util.Optional;

public interface CsvReadProcessor {
    List<String[]> read();
}
