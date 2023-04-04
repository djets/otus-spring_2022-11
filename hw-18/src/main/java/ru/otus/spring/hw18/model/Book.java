package ru.otus.spring.hw18.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.hw18.annotation.CascadeSave;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "books")
@Getter
@Setter
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

    @DBRef
    @CascadeSave
    List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return getTitle().equals(book.getTitle());
    }

    @Override
    public int hashCode() {
        return getTitle().hashCode();
    }
}
