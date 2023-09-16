package ru.otus.spring.hw24.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    String _id;

    String name;

    String surname;

    @Transient
    List<Book> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (!getName().equals(author.getName())) return false;
        return getSurname().equals(author.getSurname());
    }

//    @Override
//    public int hashCode() {
//        int result = getName().hashCode();
//        result = 31 * result + getSurname().hashCode();
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return name + " " + surname;
//    }
}
