package ru.otus.spring.dao;

import java.util.List;

public interface CsvReadProcessor {
    List<String[]> read();
}
