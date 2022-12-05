package ru.otus.spring.services;

import java.util.Map;

public interface QuestionProcessor {
    public void askQuestions();

    public Map<Long, Boolean> getResultMap();
}
