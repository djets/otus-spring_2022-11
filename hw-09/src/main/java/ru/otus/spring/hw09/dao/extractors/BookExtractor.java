package ru.otus.spring.hw09.dao.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw09.model.Author;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Deprecated
public class BookExtractor implements ResultSetExtractor<Map<Long, Book>> {
    @Override
    public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Book> longBookMap = new LinkedHashMap<>();
        while (rs.next()) {
            long bId = rs.getLong("ID");
            String bName = rs.getString("NAME");
            long aId = rs.getLong("ID");
            String aFirstName = rs.getString("FIRSTNAME");
            String aLastName = rs.getString("LASTNAME");
            long gId = rs.getLong("ID");
            String gName = rs.getString("NAME");

            longBookMap.putIfAbsent(gId, new Book(bId, bName, new Author(aId, aFirstName, aLastName), new Genre(gId, gName)));
        }
        return longBookMap;
    }
}
