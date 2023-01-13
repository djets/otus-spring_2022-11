package ru.otus.spring.hw09.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw09.dao.AuthorDao;
import ru.otus.spring.hw09.model.Author;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
    AuthorDao dao;

    @Override
    public Author getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public Long save(String name, String surname) {
        Long foundId = findByFirstNameLastName(name, surname);
        if (foundId == null) {
            Author author = new Author();
            author.setName(name);
            author.setSurname(surname);
            Long receivedId = dao.insert(author);
            logger.info("Author {} {} - save. id: {}", author.getName(), author.getSurname(), receivedId);
        }
        logger.info("Author {} {} - is already in base. id: {}", name, surname, foundId);
        return foundId;
    }

    @Override
    public void update(Long id, String name, String surname) {
        Map<String, String> changedParam = new LinkedHashMap<>();
        if (name != null) {
            changedParam.put("name", name);
        }
        if (surname != null) {
            changedParam.put("surname", surname);
        }
        int update = dao.update(id, changedParam);
        if (update > 0) {
            logger.info("Author - update");
        }
    }

    @Override
    public Long findByFirstNameLastName(String name, String surname) {
        List<Long> idList = dao.findByName(name, surname);
        if (idList.iterator().hasNext()) {
            return idList.iterator().next();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        int delete = dao.delete(id);
        if (delete > 0) {
            logger.info("Author id: {} - delete", id);
            return true;
        }
        return false;
    }

    @Override
    public List<Author> getAll() {
        return dao.findAll();
    }
}
