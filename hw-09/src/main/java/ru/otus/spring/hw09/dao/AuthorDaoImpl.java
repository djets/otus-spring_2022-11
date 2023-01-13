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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
    NamedParameterJdbcOperations operations;

    @Override
    public Long insert(@NonNull Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", author.getName());
        mapSqlParameterSource.addValue("surname", author.getSurname());
        operations.update("INSERT INTO AUTHOR (NAME, SURNAME) VALUES (:name, :surname)",
                mapSqlParameterSource, keyHolder, new String[]{"ID"});
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        author.setId(id);
        return id;
    }

    @Override
    public int update(@NonNull Long id, Map<String, String> changedParam) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        StringBuilder string = new StringBuilder("UPDATE AUTHOR SET ");
        changedParam.forEach((k, v) -> string.append(k.toUpperCase())
                .append(" = '").append(v).append("', "));
        String substring = string.substring(0, string.length() - 2);
        String sqlQuery = substring + " WHERE ID = :id";
        return operations.update(sqlQuery, mapSqlParameterSource);
    }

    @Override
    public Author findById(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return operations.queryForObject("SELECT ID, NAME, SURNAME " +
                    "FROM AUTHOR WHERE ID = :id", params, new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Author with this id was not found");
        }
    }

    @Override
    public List<Long> findByName(@NonNull String name, @NonNull String surname) {
        return operations.queryForList(
                "SELECT ID FROM AUTHOR WHERE NAME = :name AND SURNAME = :surname",
                Map.of("name", name, "surname", surname), Long.class);
    }

    @Override
    public List<Author> findAll() {
        return operations.query("SELECT ID, NAME, SURNAME FROM AUTHOR", new AuthorMapper());
    }

    @Override
    public int delete(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return operations.update("DELETE FROM AUTHOR WHERE ID =:id", params);
    }

    public static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("ID");
            String firstName = rs.getString("NAME");
            String lastName = rs.getString("SURNAME");
            return new Author(id, firstName, lastName);
        }
    }
}

