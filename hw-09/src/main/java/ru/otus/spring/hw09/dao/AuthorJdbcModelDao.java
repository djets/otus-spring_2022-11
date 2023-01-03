package ru.otus.spring.hw09.dao;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorJdbcModelDao implements ModelDao<Author, Long> {
    NamedParameterJdbcOperations operations;

    public AuthorJdbcModelDao(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public void save(@NonNull Author author) {
        operations.update("INSERT INTO AUTHOR (ID,  FIRSTNAME, LASTNAME) VALUES (:id, :firstName, :lastName);",
                Map.of("id", author.getId(),
                        "firstName", author.getFirstName(),
                        "lastName", author.getLastName()));
        if (author.getBooks() != null) {
            author.getBooks().forEach(book -> {
                operations.update("UPDATE BOOK SET AUTHOR_ID = :author WHERE ID = :bookID",
                        Map.of("author", author.getId(),
                                "bookID", book.getId()));
            });
        }
    }

    @Override
    public void save(@NonNull Long id, @NonNull Author author) {
        operations.update("UPDATE AUTHOR SET FIRSTNAME = :firstName, LASTNAME = :lastName WHERE ID = :id",
                Map.of("id", author.getId(),
                        "firstName", author.getFirstName(),
                        "lastName", author.getLastName()));
        if (author.getBooks() != null) {
            author.getBooks().forEach(book -> {
                operations.update("UPDATE BOOK SET AUTHOR_ID = :author WHERE ID = :bookID",
                        Map.of("author", author.getId(),
                                "bookID", book.getId()));
            });
        }
    }

    @Override
    public Author findById(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return operations.queryForObject(
                "SELECT ID, FIRSTNAME, LASTNAME FROM AUTHOR WHERE ID = :id",
                params, new AuthorMapper());
    }

    @Override
    public List<Author> findAll() {
        return operations.query("SELECT ID, FIRSTNAME, LASTNAME FROM AUTHOR", new AuthorMapper());
    }

    @Override
    public void delete(@NonNull Author author) {
        Map<String, Object> params = Collections.singletonMap("id", author.getId());
        operations.update("DELETE FROM AUTHOR WHERE ID =:id", params);
    }

    public static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("ID");
            String firstName = rs.getString("FIRSTNAME");
            String lastName = rs.getString("LASTNAME");
            return new Author(id, firstName, lastName, new ArrayList<>());
        }
    }
}

