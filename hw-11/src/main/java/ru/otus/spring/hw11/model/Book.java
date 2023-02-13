package ru.otus.spring.hw11.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    long id;

    @Column(name = "name")
    String name;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"),
            foreignKey = @ForeignKey(name = "fk_book_author_on_book"),
            inverseForeignKey = @ForeignKey(name = "fk_book_author_on_author")
    )
    List<Author> authors;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
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

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setBook(null);

    }
}
