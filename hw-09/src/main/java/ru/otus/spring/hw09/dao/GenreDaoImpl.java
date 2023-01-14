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
import ru.otus.spring.hw09.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    NamedParameterJdbcOperations operations;

    @Override
    public Long insert(@NonNull Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", genre.getName());
        operations.update("INSERT INTO GENRE (NAME) VALUES (:name)",
                mapSqlParameterSource, keyHolder, new String[]{"id"});
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        genre.setId(id);
        return id;
    }

    @Override
    public int update(@NonNull Long id, String changedName) {
        return operations.update("UPDATE GENRE SET NAME = :name WHERE ID = :id",
                Map.of("id", id, "name", changedName));
    }

    @Override
    public Genre findById(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return operations.queryForObject("SELECT ID, NAME " + "FROM GENRE " + "WHERE ID = :id", params, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Genre with this id was not found");
        }
    }

    @Override
    public List<Long> findByName(@NonNull String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return operations.queryForList("SELECT ID FROM GENRE WHERE NAME = :name", params, Long.class);
    }

    @Override
    public List<Genre> findAll() {
        return operations.query("SELECT ID, NAME FROM GENRE", new GenreMapper());
    }

    @Override
    public int delete(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return operations.update("DELETE FROM GENRE WHERE ID =:id", params);
    }

    public static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("ID");
            String name = rs.getString("NAME");
            return new Genre(id, name);
        }
    }
}
