package service;

import com.opencsv.exceptions.CsvException;
import dao.AbstractDao;
import model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class QuestionService implements AbstractService<Question> {
    static Logger log = LoggerFactory.getLogger(QuestionService.class);
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
