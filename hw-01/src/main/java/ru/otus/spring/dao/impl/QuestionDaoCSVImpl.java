package ru.otus.spring.dao.impl;

import com.opencsv.exceptions.CsvException;
import ru.otus.spring.config.ConfigCSV;
import ru.otus.spring.dao.AbstractDao;
import ru.otus.spring.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuestionDaoCSVImpl extends AbstractDaoCSV implements AbstractDao<Question> {
    private static Logger log = LoggerFactory.getLogger(QuestionDaoCSVImpl.class);

//    @Override
//    public void setFileNameCSV(ConfigCSV fileNameCSV) {
//        super.setFileNameCSV(fileNameCSV);
//    }

    @Override
    public Optional<List<Question>> getAll() throws IOException, CsvException {
        return Optional.of(getCsvReader().readAll().stream().map(s -> Question.builder().setText(s[0]).build()).distinct().collect(Collectors.toList()));
    }

    @Override
    public Optional<Question> get(String text) {
        return Optional.empty();
    }
}
