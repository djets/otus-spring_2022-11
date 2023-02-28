package ru.otus.spring.hw15.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @SequenceGenerator(
            name = "comment_generator",
            sequenceName = "comment_seq",
            initialValue = 1000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_generator"
    )
    Long id;
    @Column(name = "text_comment", length = 3000)
    String textComment;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "create_data",
            nullable = false,
            updatable = false)
    Date createData;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_book_comments")
    )
    Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!getTextComment().equals(comment.getTextComment())) return false;
        return getCreateData() != null ? getCreateData().equals(comment.getCreateData()) : comment.getCreateData() == null;
    }

    @Override
    public int hashCode() {
        int result = getTextComment().hashCode();
        result = 31 * result + (getCreateData() != null ? getCreateData().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return createData.toString() + ";\n" + textComment;
    }
}
