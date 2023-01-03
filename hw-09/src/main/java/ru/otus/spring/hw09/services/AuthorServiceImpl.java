package ru.otus.spring.hw09.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw09.dao.AuthorJdbcModelDao;
import ru.otus.spring.hw09.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
    AuthorJdbcModelDao dao;

    @Override
    public void save(Author author) {
        dao.save(author);
        logger.info("Author {} {} - save", author.getFirstName(), author.getLastName());
    }

    @Override
    public void save(Long id, Author author) {
        dao.save(id, author);
        logger.info("Author {} {} - update", author.getFirstName(), author.getLastName());
    }

    @Override
    public Long saveIfAbsentFirstNameLastName(String authorFirstName, String authorLastName) {
        Author author = new Author();
        Long id = null;
        for (Author a : dao.findAll()) {
            if (a.getFirstName().equals(authorFirstName) && a.getLastName().equals(authorLastName)) {
                id = a.getId();
                throw new RuntimeException("There is already an author with the same Name and Surname" +  a.getId());
            }
        }
        if (author.getId() == null) {
            id = UUID.randomUUID().getMostSignificantBits();
            author.setId(id);
            author.setFirstName(authorFirstName);
            author.setLastName(authorLastName);
            author.setBooks(new ArrayList<>());
            dao.save(author);
            return id;
        }
        return id;
    }

    @Override
    public Author getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void delete(Long id) {
        Author author = new Author();
        author.getId();
        dao.delete(author);
        logger.info("Author id: {} - delete", id);
    }

    @Override
    public List<Author> getAll() {
        return dao.findAll();
    }
}
