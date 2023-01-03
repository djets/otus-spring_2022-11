package ru.otus.spring.hw09.dao;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookJdbcModelDao implements ModelDao<Book, Long> {
    NamedParameterJdbcOperations operations;

    public BookJdbcModelDao(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public void save(@NonNull Book book) {
        operations.update("INSERT INTO BOOK (ID,  NAME, AUTHOR_ID, GENRE_ID) VALUES (:id, :name, :author, :genre)",
                Map.of("id", book.getId(),
                        "name", book.getName(),
                        "author", book.getAuthor().getId(),
                        "genre", book.getGenre().getId()));
    }

    @Override
    public void save(@NonNull Long id, @NonNull Book book) {
        operations.update("UPDATE BOOK SET ID = :id,  NAME = :name, AUTHOR_ID = :author, GENRE_ID = :genre WHERE ID = :id",
                Map.of("id", book.getId(),
                        "name", book.getName(),
                        "author", book.getAuthor().getId(),
                        "genre", book.getGenre().getId()));
    }

    @Override
    public Book findById(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return operations.queryForObject(
                "SELECT B.ID, B.NAME, A.ID, A.FIRSTNAME, A.LASTNAME, G.ID, G.NAME " +
                        "FROM BOOK B " +
                        "JOIN AUTHOR A ON A.ID = B.AUTHOR_ID " +
                        "JOIN GENRE G ON G.ID = B.GENRE_ID " +
                        "WHERE B.ID = :id",
                params,
                new BookMapper());
    }

    @Override
    public List<Book> findAll() {
        return operations.query(
                "SELECT B.ID, B.NAME, A.ID, A.FIRSTNAME, A.LASTNAME, G.ID, G.NAME " +
                        "FROM BOOK B " +
                        "JOIN AUTHOR A ON A.ID = B.AUTHOR_ID " +
                        "JOIN GENRE G ON G.ID = B.GENRE_ID ",
                new BookMapper());
    }

    @Override
    public void delete(Book book) {
        Map<String, Object> params = Collections.singletonMap("id", book.getId());
        operations.update("DELETE FROM BOOK WHERE ID =:id", params);
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            long bId = rs.getLong("ID");
            String bName = rs.getString("NAME");
            long aId = rs.getLong("ID");
            String aFirstName = rs.getString("FIRSTNAME");
            String aLastName = rs.getString("LASTNAME");
            long gId = rs.getLong("ID");
            String gName = rs.getString("NAME");
            Book book = new Book(bId, bName,
                    new Author(aId, aFirstName, aLastName, new ArrayList<>()),
                    new Genre(gId, gName, new ArrayList<>()));
            book.getAuthor().getBooks().add(book);
            book.getGenre().getBooks().add(book);
            return book;
        }
    }
}
