package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import ru.otus.spring.dao.AbstractDao;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.List;

public class QuestionService implements AbstractService<Question> {
    private final AbstractDao<Question> questionDao;

    public QuestionService(AbstractDao<Question> questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getListObject() {
        try {
            return questionDao.getAll().get();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Question getObject(String text) {
        return questionDao.get(text).get();
    }

}
