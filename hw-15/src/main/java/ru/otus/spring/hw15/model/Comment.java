package ru.otus.spring.hw15.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "comments")
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    String _id;

    String textComment;

    Date createData = new Date();

    @DBRef
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
