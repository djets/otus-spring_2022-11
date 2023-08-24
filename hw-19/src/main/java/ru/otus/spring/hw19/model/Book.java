package ru.otus.spring.hw19.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw19.annotation.CascadeSave;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    String _id;

    String title;

    @DBRef
    @CascadeSave
    List<Author> authors = new ArrayList<>();

    @DBRef
    @CascadeSave
    Genre genre;
}
