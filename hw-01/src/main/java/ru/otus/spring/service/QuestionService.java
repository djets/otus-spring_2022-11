package ru.otus.spring.service;

import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.Map;
import java.util.Set;

public interface QuestionService {
    Map<Question, Set<Answer>> getQuestions();
    void printAllQuestions();
}
