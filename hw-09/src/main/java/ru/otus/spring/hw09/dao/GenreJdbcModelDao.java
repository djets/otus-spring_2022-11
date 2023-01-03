package ru.otus.spring.hw09.dao;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw09.dao.extractors.GenreExtractor;
import ru.otus.spring.hw09.model.Genre;

import java.util.*;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreJdbcModelDao implements ModelDao<Genre, Long>{
    NamedParameterJdbcOperations operations;
    GenreExtractor genreExtractor;

    @Override
    public void save(@NonNull Genre genre) {
        operations.update("INSERT INTO GENRE (ID, NAME) VALUES (:id, :name)",
                Map.of("id", genre.getId(), "name", genre.getName()));
        if (genre.getBooks() != null) {
            genre.getBooks().forEach(book -> {
                operations.update("UPDATE BOOK SET GENRE_ID = :genre WHERE ID = :bookID",
                        Map.of("genre", genre.getId(),
                                "bookID", book.getId()));
            });
        }
    }

    @Override
    public void save(@NonNull Long id, @NonNull Genre genre) {
        operations.update("UPDATE GENRE SET ID = :id, NAME = :name WHERE ID = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
        if (genre.getBooks() != null) {
            genre.getBooks().forEach(book -> {
                operations.update("UPDATE BOOK SET GENRE_ID = :genre WHERE ID = :bookID",
                        Map.of("genre", genre.getId(),
                                "bookID", book.getId()));
            });
        }
    }

    @Override
    public Genre findById(@NonNull Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Map<Long, Genre> longGenreMap = operations.query(
                "SELECT G.ID, G.NAME, B.ID, B.NAME, A.ID, A.FIRSTNAME, A.LASTNAME " +
                        "FROM GENRE G " +
                        "LEFT JOIN BOOK B on G.ID = B.GENRE_ID " +
                        "LEFT JOIN AUTHOR A on A.ID = B.AUTHOR_ID " +
                        "WHERE G.ID = :id",
                params,
                genreExtractor);
        return Objects.requireNonNull(longGenreMap).get(id);
    }

    @Override
    public List<Genre> findAll() {
        Map<Long, Genre> longGenreMap = operations.query(
                "SELECT G.ID, G.NAME, B.ID, B.NAME, A.ID, A.FIRSTNAME, A.LASTNAME " +
                        "FROM GENRE G " +
                        "LEFT OUTER JOIN BOOK B on G.ID = B.GENRE_ID " +
                        "LEFT OUTER JOIN AUTHOR A on A.ID = B.AUTHOR_ID",
                genreExtractor);
        return new ArrayList<>(Objects.requireNonNull(longGenreMap).values());
    }

    @Override
    public void delete(@NonNull Genre genre) {
        Map<String, Object> params = Collections.singletonMap("id", genre.getId());
        operations.update("DELETE FROM GENRE WHERE ID =:id",
                params);
    }
}
