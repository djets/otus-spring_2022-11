package ru.otus.spring.hw15.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "book-only-entity-graph"),
        @NamedEntityGraph(name = "book-genre-authors-entity-graph",
                attributeNodes = {
                    @NamedAttributeNode(value = "genre"),
                    @NamedAttributeNode(value = "authors"),
            }),
})
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_generator",
            sequenceName = "book_seq",
            initialValue = 1000,
            allocationSize = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_generator"
    )
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "name")
    String name;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"),
            foreignKey = @ForeignKey(name = "fk_book_author_on_book"),
            inverseForeignKey = @ForeignKey(name = "fk_book_author_on_author")
    )
    List<Author> authors = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    @JoinColumn(
            name = "genre_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_genre_books_id")
    )
    Genre genre;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return getName().equals(book.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
