package ru.otus.spring.hw11.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    @SequenceGenerator(
            name = "author_generator",
            sequenceName = "author_seq",
            initialValue = 1000,
            allocationSize = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_generator"
    )
    long id;
    @Column(name = "name")
    String name;
    @Column(name = "surname")
    String surname;
    @ManyToMany(
            mappedBy = "authors",
            fetch = FetchType.LAZY
    )
    List<Book> books;
}
