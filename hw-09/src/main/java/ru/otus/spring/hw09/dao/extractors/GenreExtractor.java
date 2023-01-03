package ru.otus.spring.hw09.dao.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GenreExtractor implements ResultSetExtractor<Map<Long, Genre>> {
    @Override
    public Map<Long, Genre> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Genre> longGenreMap = new LinkedHashMap<>();
        while (rs.next()) {
            long gId = rs.getLong("ID");
            String gName = rs.getString("NAME");
            long bId = rs.getLong("ID");
            String bName = rs.getString("NAME");
            long aId = rs.getLong("ID");
            String aFirstName = rs.getString("FIRSTNAME");
            String aLastName = rs.getString("LASTNAME");

            longGenreMap.putIfAbsent(gId, new Genre(gId, gName, new ArrayList<>()));
            Book book = new Book();
            book.setId(bId);
            book.setName(bName);
            book.setAuthor(new Author(aId, aFirstName, aLastName, new ArrayList<>()));
            longGenreMap.get(gId).getBooks().add(book);
        }
        return longGenreMap;
    }
}
