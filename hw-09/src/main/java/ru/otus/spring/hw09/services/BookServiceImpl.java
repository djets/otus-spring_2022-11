package ru.otus.spring.hw09.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw09.dao.BookJdbcModelDao;
import ru.otus.spring.hw09.model.Book;
import ru.otus.spring.hw09.model.Genre;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
    BookJdbcModelDao dao;
    @Override
    public Book getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void save(Book book) {
        dao.save(book);
        logger.info("Book {} - save", book.getName());
    }

    @Override
    public void save(Long id, Book book) {
        dao.save(id, book);
        logger.info("Book {} - update", book.getName());
    }

    @Override
    public Long saveIfAbsentName(String name) {
        Book book = new Book();
        Long id = null;
        for (Book b : dao.findAll()) {
            if (b.getName().equals(name)) {
                throw new RuntimeException("There is already an book with the same Name" +  b.getId());
            }
        }
        if (book.getId() == null) {
            id = UUID.randomUUID().getMostSignificantBits();
            book.setId(id);
            book.setName(name);
            dao.save(book);
        }
        return id;
    }

    @Override
    public void delete(Long id) {
        Book book = new Book();
        book.setId(id);
        dao.delete(book);
        logger.info("Book id: {} - delete", id);
    }

    @Override
    public List<Book> getAll() {
        return dao.findAll();
    }
}
