package ru.otus.spring.hw22.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "comments")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    String _id;

    String textComment;

    Date createData;

    String bookId;

    @Override
    public String toString() {
        return createData.toString() + ";\n" + textComment;
    }
}
