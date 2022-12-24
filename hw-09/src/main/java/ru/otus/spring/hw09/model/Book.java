package ru.otus.spring.hw09.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    Long id;
    String name;
    Author author;
    Genre genre;
}
