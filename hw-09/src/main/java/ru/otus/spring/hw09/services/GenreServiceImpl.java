package ru.otus.spring.hw09.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw09.dao.GenreDao;
import ru.otus.spring.hw09.model.Genre;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);
    GenreDao dao;

    @Override
    public Genre getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public Long save(String name) {
        Long foundId = findByName(name);
        if (foundId == 0) {
            Genre genre = new Genre();
            genre.setName(name);
            Long receivedId = dao.insert(genre);
            logger.info("Genre {} - save. id: {}", genre.getName(), receivedId);
        }
        logger.info("Genre {} - is already in base. id: {}", name, foundId);
        return foundId;

    }

    @Override
    public void update(Long id, String changedName) {
        int update = dao.update(id, changedName);
        if (update > 0) {
            logger.info("Genre {} - update", changedName);
        }
    }

    @Override
    public Long findByName(String name) {
        List<Long> idList = dao.findByName(name);
        if (idList.iterator().hasNext()) {
            return idList.iterator().next();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        int delete = dao.delete(id);
        if (delete > 0) {
            logger.info("Genre id: {} - delete", id);
            return true;
        }
        return false;
    }

    @Override
    public List<Genre> getAll() {
        return dao.findAll();
    }
}
