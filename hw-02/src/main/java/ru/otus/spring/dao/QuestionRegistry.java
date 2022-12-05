package ru.otus.spring.dao;

import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.List;

public interface QuestionRegistry {
    Question getQuestionById(Long id);

    Answer getAnswerById(Long id);

    List<Question> getQuestionList();
}
