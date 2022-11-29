package ru.otus.spring.dao.impl;

import com.opencsv.exceptions.CsvException;
import ru.otus.spring.dao.AbstractDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnswerDaoCSVImpl extends AbstractDaoCSV implements AbstractDao<Answer> {

    @Override
    public Optional<List<Answer>> getAll() throws IOException, CsvException {
        return Optional.of(getCsvReader().readAll().stream().map(s -> Answer.builder().setText(s[1]).setRightAnswer(Boolean.parseBoolean(s[2])).setQuestion(Question.builder().setText(s[0]).build()).build()).distinct().collect(Collectors.toList()));
    }

    @Override
    public Optional<Answer> get(String text) {
        return Optional.empty();
    }
}
