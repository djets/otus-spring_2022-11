package ru.otus.spring.hw09.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw09.dao.BookDao;
import ru.otus.spring.hw09.model.Book;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    AuthorService authorService;
    GenreService genreService;
    BookDao bookDao;

    @Override
    public Book getById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public Long save(String name, String authorName, String authorSurname, String genreName) {
        Long foundIdBookByName = findByName(name);
        Long authorId = null;
        Long genreId = null;
        if (authorName != null && authorSurname != null) {
            authorId = authorService.save(authorName, authorSurname);
        }
        if (genreName != null) {
            genreId = genreService.save(genreName);
        }
        if (foundIdBookByName == null) {
            Book book = new Book();
            book.setName(name);
            if (authorId != null) {
                book.setAuthor(authorService.getById(authorId));
            }
            if (genreId != null) {
                book.setGenre(genreService.getById(genreId));
            }
            Long receivedId = bookDao.insert(book);
            logger.info("Book {} - save. id: {}", book.getName(), receivedId);
            return receivedId;
        }
        logger.info("Book {} - is already in base. id: {}", name, foundIdBookByName);
        return foundIdBookByName;
    }

    @Override
    public void update(Long id, String authorNameSurname, String genreName) {
        Map<String, Object> changedParam = new LinkedHashMap<>();
        if (authorNameSurname != null) {
            String[] split = authorNameSurname.split(" +");
            Long receivedId = authorService.save(split[0], split[1]);
            if (receivedId != null) {
                changedParam.put("author_id", receivedId);
            }
        }
        if (genreName != null) {
            Long receivedId = genreService.save(genreName);
            if (receivedId != null) {
                changedParam.put("genre_id", receivedId);
            }
        }
        int update = bookDao.update(id, changedParam);
        if (update > 0) {
            logger.info("Book - update");
        }
    }

    @Override
    public Long findByName(String name) {
        List<Long> idList = bookDao.findByName(name);
        if (idList.size() > 0) {
            return idList.iterator().next();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        int delete = bookDao.delete(id);
        if (delete > 0) {
            logger.info("Book id: {} - delete", id);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }
}
