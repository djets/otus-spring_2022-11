package ru.otus.spring.hw09.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw09.dao.GenreJdbcModelDao;
import ru.otus.spring.hw09.model.Genre;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
    GenreJdbcModelDao dao;
    @Override
    public Genre getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void save(Genre genre) {
        dao.save(genre);
        logger.info("Genre {} - save", genre.getName());
    }

    @Override
    public void save(Long id, Genre genre) {
        dao.save(id, genre);
        logger.info("Genre {} - update", genre.getName());
    }

    @Override
    public Long saveIfAbsentName(String name) {
        Genre genre = new Genre();
        Long id = null;
        for (Genre g : dao.findAll()) {
            if (g.getName().equals(name)) {
                throw new RuntimeException("There is already an genre with the same Name" +  g.getId());
            }
        }
        if (genre.getId() == null) {
            id = UUID.randomUUID().getMostSignificantBits();
            genre.setId(id);
            genre.setName(name);
            dao.save(genre);
        }
        return id;
    }

    @Override
    public void delete(Long id) {
        Genre genre = new Genre();
        genre.setId(id);
        dao.delete(genre);
        logger.info("Genre id: {} - delete",  id);
    }

    @Override
    public List<Genre> getAll() {
        return dao.findAll();
    }
}
