package ru.otus.spring.service;

import ru.otus.spring.controller.OutputController;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionsDao questionDao;
    public final OutputController consoleOutput;

    public QuestionServiceImpl(QuestionsDao questionDao, OutputController consoleOutput) {
        this.questionDao = questionDao;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public Map<Question, Set<Answer>> getQuestions() {
        return questionDao.getAll().get();
    }

    @Override
    public void printAllQuestions() {
        getQuestions().forEach((k, v) -> {
            var i = new AtomicInteger(1);
            consoleOutput.stdout(k.getText() + "\n" + v.stream()
                    .map(Answer::getText)
                    .map(s -> i.getAndIncrement() + ": " + s)
                    .collect(Collectors.joining("\n")));
        });
    }
}
