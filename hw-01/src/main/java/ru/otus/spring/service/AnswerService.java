package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import ru.otus.spring.dao.AbstractDao;
import ru.otus.spring.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class AnswerService implements AbstractService<Answer> {
    static Logger log = LoggerFactory.getLogger(AnswerService.class);
    private final AbstractDao<Answer> answerDao;

    public AnswerService(AbstractDao<Answer> answerDao) {
        this.answerDao = answerDao;
    }


    @Override
    public List<Answer> getListObject() {
        try {
            return answerDao.getAll().get();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Answer getObject(String text) {
        return answerDao.get(text).get();
    }
}
