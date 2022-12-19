package ru.otus.spring.services;

import java.util.Map;

public interface QuestionProcessor {
    public void askQuestions(Long userId);

    public Map<Long, Boolean> getResultMapByUserId(Long userId);
}
