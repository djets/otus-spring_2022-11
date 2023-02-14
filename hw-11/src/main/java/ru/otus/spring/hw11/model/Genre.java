package ru.otus.spring.hw11.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "genres")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Genre {
    @Id
    @SequenceGenerator(
            name = "genre_generator",
            sequenceName = "genre_seq",
            initialValue = 1000,
            allocationSize = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_generator"
    )
    @Column(name = "id", nullable = false)
    long id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "genre")
    List<Book> books = new ArrayList<>();

    public Genre(long id, String name) {
    }

    public void addBook(Book book) {
        books.add(book);
        book.setGenre(this);
    }

    public void removeBooks(Book book) {
        books.remove(book);
        book.setGenre(null);
    }
}
