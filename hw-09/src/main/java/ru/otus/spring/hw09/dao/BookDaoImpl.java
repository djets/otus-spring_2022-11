package ru.otus.spring.hw09.dao;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    NamedParameterJdbcOperations operations;

    @Override
    public Long insert(@NonNull Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", book.getName());
        mapSqlParameterSource.addValue("author_id", book.getAuthor() != null ? book.getAuthor().getId() : null);
        mapSqlParameterSource.addValue("genre_id", book.getGenre() != null ? book.getGenre().getId() : null);

        operations.update("INSERT INTO BOOK (NAME, AUTHOR_ID, GENRE_ID) " +
                "VALUES (:name, :author_id, :genre_id)", mapSqlParameterSource, keyHolder, new String[]{"ID"});
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        book.setId(id);
        return id;
    }

    @Override
    public int update(@NonNull Long id, Map<String, Object> changedParam) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        StringBuilder string = new StringBuilder("UPDATE BOOK SET ");
        changedParam.forEach((k, v) -> string.append(k.toUpperCase())
                .append(" = '").append(v).append("', "));
        String substring = string.substring(0, string.length() - 2);
        String sqlQuery = substring + " WHERE ID = :id";
        return operations.update(sqlQuery, mapSqlParameterSource);
    }

    @Override
    public Book findById(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return operations.queryForObject("SELECT B.ID, B.NAME, A.ID, A.NAME, A.SURNAME, G.ID, G.NAME " +
                            "FROM BOOK B " +
                            "LEFT JOIN AUTHOR A ON A.ID = B.AUTHOR_ID " +
                            "LEFT JOIN GENRE G ON G.ID = B.GENRE_ID " +
                            "WHERE B.ID = :id",
                    params,
                    new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Book with this id was not found");
        }
    }

    @Override
    public List<Long> findByName(@NonNull String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return operations.queryForList(
                "SELECT ID FROM BOOK WHERE NAME = :name", params, Long.class);
    }

    @Override
    public List<Book> findAll() {
        return operations.query(
                "SELECT B.ID, B.NAME, A.ID, A.NAME, A.SURNAME, G.ID, G.NAME " +
                        "FROM BOOK B " +
                        "JOIN AUTHOR A ON A.ID = B.AUTHOR_ID " +
                        "JOIN GENRE G ON G.ID = B.GENRE_ID ",
                new BookMapper());
    }

    @Override
    public int delete(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return operations.update("DELETE FROM BOOK WHERE ID =:id", params);
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<Integer, Object> objectMap = new LinkedHashMap<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                objectMap.put(i, rs.getObject(i));
            }
            Book book = new Book();
            book.setId((Long) objectMap.get(1));
            book.setName((String) objectMap.get(2));
            if(objectMap.get(3) != null) {
                book.setAuthor(new Author((Long) objectMap.get(3), (String) objectMap.get(4), (String) objectMap.get(5)));
            }
            if (objectMap.get(6) != null) {
                book.setGenre(new Genre((Long) objectMap.get(6), (String) objectMap.get(7)));
            }
            return book;
        }
    }
}
